from rest_framework.permissions import AllowAny, IsAuthenticated
from django.http import HttpResponse
from django.shortcuts import render, redirect
from django.contrib.auth import login, logout, authenticate, get_user_model
from sendgrid import SendGridAPIClient
from django.template.loader import get_template
from sendgrid.helpers.mail import *
from root.settings import SENDGRID_API_KEY
from django.contrib.sites.shortcuts import get_current_site
from django.utils.encoding import force_bytes, force_text
from django.utils.http import urlsafe_base64_encode, urlsafe_base64_decode
from django.template.loader import render_to_string
from .tokens import account_activation_token
from rest_framework.response import Response
from rest_framework import status, serializers, exceptions
from rest_framework.decorators import api_view, permission_classes
from rest_framework.permissions import AllowAny
from .serializers import RegistrationSerializer
from rest_framework.authtoken.models import Token
from django.contrib.auth import get_user_model
from rest_framework.status import HTTP_200_OK, HTTP_400_BAD_REQUEST
from rest_framework.views import APIView
from .serializers import UserLoginSerializer

from datetime import timedelta
from django.core.exceptions import ValidationError
from django.contrib.auth.password_validation import validate_password, get_password_validators
from django.utils.translation import ugettext_lazy as _
from django.utils import timezone
from django.conf import settings
from rest_framework.generics import GenericAPIView
from rest_framework.response import Response

from django_rest_passwordreset.serializers import EmailSerializer, PasswordTokenSerializer, TokenSerializer
from django_rest_passwordreset.models import ResetPasswordToken, clear_expired, get_password_reset_token_expiry_time, \
    get_password_reset_lookup_field
from django_rest_passwordreset.signals import reset_password_token_created, pre_password_reset, post_password_reset

User = get_user_model()


class UserLoginView(APIView):
    permission_classes = [AllowAny]
    serializer_class = UserLoginSerializer
    def post(self, request, *args, **kwargs):
        data = request.data
        serializer = UserLoginSerializer(data = data)
        if serializer.is_valid(raise_exception=True):
            data = serializer.save()
            return Response(data, status=HTTP_200_OK)
        return  Response(serializer.errors, status=HTTP_400_BAD_REQUEST)

class UserLogoutView(APIView):
    permissions_classes = [IsAuthenticated]
    def get(self, request):
        logout(request)
        return Response(status=HTTP_200_OK)


@api_view(['POST', ])
@permission_classes([AllowAny, ])
def api_register_view(request):
    organization_email = request.data.get('organization_email')
    serializer = RegistrationSerializer(data=request.data)
    data = {}
    if serializer.is_valid():
        account = serializer.save()
        data['token'] = Token.objects.get(user=account).key
        data['user'] = serializer.data
        current_site = get_current_site(request)
        sg = SendGridAPIClient(SENDGRID_API_KEY)
        message = render_to_string('email_verification_template.html', {
            'user': account,
            'domain': current_site.domain,
            'uid': urlsafe_base64_encode(force_bytes(account.pk)),
            'token': account_activation_token.make_token(account),
        })
        content = Content("text/html", message)
        mail_subject = 'Activate your account.'
        to_email = serializer.validated_data.get('email')
        mail = Mail(organization_email, to_email, mail_subject, content)
        sg.send(mail)
    else:
        data['errors'] = serializer.errors
    return Response(data, status=status.HTTP_201_CREATED)


@api_view(['GET', ])
@permission_classes([AllowAny, ])
def activate(request, uidb64, token):
    User = get_user_model()
    try:
        uid = force_text(urlsafe_base64_decode(uidb64))
        user = User.objects.get(pk=uid)
    except(TypeError, ValueError, OverflowError, User.DoesNotExist):
        user = None
    if user is not None and account_activation_token.check_token(user, token):
        user.is_active = True
        user.save()
        return Response('Account Has Been Activated')
    else:
        return HttpResponse('Confirmation link is invalid!')


__all__ = [
    'ValidateToken',
    'ResetPasswordConfirm',
    'ResetPasswordRequestToken',
    'reset_password_validate_token',
    'reset_password_confirm',
    'reset_password_request_token'
]

HTTP_USER_AGENT_HEADER = getattr(settings, 'DJANGO_REST_PASSWORDRESET_HTTP_USER_AGENT_HEADER', 'HTTP_USER_AGENT')
HTTP_IP_ADDRESS_HEADER = getattr(settings, 'DJANGO_REST_PASSWORDRESET_IP_ADDRESS_HEADER', 'REMOTE_ADDR')


class ResetPasswordValidateToken(GenericAPIView):
    """
    An Api View which provides a method to verify that a token is valid
    """
    throttle_classes = ()
    permission_classes = ()
    serializer_class = TokenSerializer

    def post(self, request, *args, **kwargs):
        serializer = self.serializer_class(data=request.data)
        serializer.is_valid(raise_exception=True)
        token = serializer.validated_data['token']

        # get token validation time
        password_reset_token_validation_time = get_password_reset_token_expiry_time()

        # find token
        reset_password_token = ResetPasswordToken.objects.filter(key=token).first()

        if reset_password_token is None:
            return Response({'status': 'notfound'}, status=status.HTTP_404_NOT_FOUND)

        # check expiry date
        expiry_date = reset_password_token.created_at + timedelta(hours=password_reset_token_validation_time)

        if timezone.now() > expiry_date:
            # delete expired token
            reset_password_token.delete()
            return Response({'status': 'expired'}, status=status.HTTP_404_NOT_FOUND)
        
        return Response({'status': 'OK'})


class ResetPasswordConfirm(GenericAPIView):
    """
    An Api View which provides a method to reset a password based on a unique token
    """
    throttle_classes = ()
    permission_classes = ()
    serializer_class = PasswordTokenSerializer

    def post(self, request, *args, **kwargs):
        serializer = self.serializer_class(data=request.data)
        serializer.is_valid(raise_exception=True)
        password = serializer.validated_data['password']
        token = serializer.validated_data['token']

        # get token validation time
        password_reset_token_validation_time = get_password_reset_token_expiry_time()

        # find token
        reset_password_token = ResetPasswordToken.objects.filter(key=token).first()

        if reset_password_token is None:
            return Response({'status': 'notfound'}, status=status.HTTP_404_NOT_FOUND)

        # check expiry date
        expiry_date = reset_password_token.created_at + timedelta(hours=password_reset_token_validation_time)

        if timezone.now() > expiry_date:
            # delete expired token
            reset_password_token.delete()
            return Response({'status': 'expired'}, status=status.HTTP_404_NOT_FOUND)

        # change users password (if we got to this code it means that the user is_active)
        if reset_password_token.user.eligible_for_reset():
            pre_password_reset.send(sender=self.__class__, user=reset_password_token.user)
            try:
                # validate the password against existing validators
                validate_password(
                    password,
                    user=reset_password_token.user,
                    password_validators=get_password_validators(settings.AUTH_PASSWORD_VALIDATORS)
                )
            except ValidationError as e:
                # raise a validation error for the serializer
                raise exceptions.ValidationError({
                    'password': e.messages
                })

            reset_password_token.user.set_password(password)
            reset_password_token.user.save()
            post_password_reset.send(sender=self.__class__, user=reset_password_token.user)

        # Delete all password reset tokens for this user
        ResetPasswordToken.objects.filter(user=reset_password_token.user).delete()

        return Response({'status': 'OK'})


class ResetPasswordRequestToken(GenericAPIView):
    """
    An Api View which provides a method to request a password reset token based on an e-mail address
    Sends a signal reset_password_token_created when a reset token was created
    """
    throttle_classes = ()
    permission_classes = ()
    serializer_class = EmailSerializer

    def post(self, request, *args, **kwargs):
        serializer = self.serializer_class(data=request.data)
        serializer.is_valid(raise_exception=True)
        email = serializer.validated_data['email']

        # before we continue, delete all existing expired tokens
        password_reset_token_validation_time = get_password_reset_token_expiry_time()

        # datetime.now minus expiry hours
        now_minus_expiry_time = timezone.now() - timedelta(hours=password_reset_token_validation_time)

        # delete all tokens where created_at < now - 24 hours
        clear_expired(now_minus_expiry_time)

        # find a user by email address (case insensitive search)
        users = User.objects.filter(**{'{}__iexact'.format(get_password_reset_lookup_field()): email})

        active_user_found = False

        # iterate over all users and check if there is any user that is active
        # also check whether the password can be changed (is useable), as there could be users that are not allowed
        # to change their password (e.g., LDAP user)
        for user in users:
            if user.eligible_for_reset():
                active_user_found = True

        # No active user found, raise a validation error
        # but not if DJANGO_REST_PASSWORDRESET_NO_INFORMATION_LEAKAGE == True
        if not active_user_found and not getattr(settings, 'DJANGO_REST_PASSWORDRESET_NO_INFORMATION_LEAKAGE', False):
            raise exceptions.ValidationError({
                'email': [_(
                    "There is no active user associated with this e-mail address or the password can not be changed")],
            })

        # last but not least: iterate over all users that are active and can change their password
        # and create a Reset Password Token and send a signal with the created token
        for user in users:
            if user.eligible_for_reset():
                # define the token as none for now
                token = None

                # check if the user already has a token
                if user.password_reset_tokens.all().count() > 0:
                    # yes, already has a token, re-use this token
                    token = user.password_reset_tokens.all()[0]
                else:
                    # no token exists, generate a new token
                    token = ResetPasswordToken.objects.create(
                        user=user,
                        user_agent=request.META.get(HTTP_USER_AGENT_HEADER, ''),
                        ip_address=request.META.get(HTTP_IP_ADDRESS_HEADER, ''),
                    )
                # send a signal that the password token was created
                # let whoever receives this signal handle sending the email for the password reset
                reset_password_token_created.send(sender=self.__class__, instance=self, reset_password_token=token)
        # done
        return Response({'status': 'OK'})


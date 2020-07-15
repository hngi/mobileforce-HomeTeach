from rest_framework.permissions import AllowAny, IsAuthenticated
from django.http import HttpResponse
from rest_framework.request import Request
from django.contrib.auth import login, logout, authenticate
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
User = get_user_model()


class UserLoginView(APIView):
    permission_classes = [AllowAny]
    serializer_class = UserLoginSerializer
    def post(self, request, *args, **kwargs):
        data = request.data
        serializer = UserLoginSerializer(data = data, context={'request': request})
        if serializer.is_valid(raise_exception=True):
            data = serializer.save()
            return Response(serializer.data, status=HTTP_200_OK)
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

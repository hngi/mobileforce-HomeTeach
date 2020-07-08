from rest_framework.response import Response
from rest_framework import status
from rest_framework.decorators import api_view, permission_classes
from rest_framework.permissions import AllowAny
from .serializers import RegistrationSerializer
from rest_framework.authtoken.models import Token
from django.http import HttpResponse
from django.shortcuts import render, redirect
from django.contrib.auth import login, authenticate
from sendgrid import SendGridAPIClient
from django.template.loader import get_template
from sendgrid.helpers.mail import *
from root.local import SENDGRID_API_KEY
from django.contrib.sites.shortcuts import get_current_site
from django.utils.encoding import force_bytes, force_text
from django.utils.http import urlsafe_base64_encode, urlsafe_base64_decode
from django.template.loader import render_to_string
from .tokens import account_activation_token
from django.contrib.auth import get_user_model
from django.core.mail import EmailMessage



@api_view(['POST', ])
@permission_classes([AllowAny, ])
def api_register_view(request):
    organization_email = request.POST.get('organization_email')
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
    except(TypeError, ValueError, OverflowError, user.DoesNotExist):
        user = None
    if user is not None and account_activation_token.check_token(user, token):
        user.is_active = True
        user.save()
        return Response('Account Has Been Activated')
    else:
        return HttpResponse('Confirmation link is invalid!')
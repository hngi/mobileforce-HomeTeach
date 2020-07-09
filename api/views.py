from django.http import HttpResponse
from sendgrid import SendGridAPIClient
from django.template.loader import get_template
from sendgrid.helpers.mail import *
from django.contrib.sites.shortcuts import get_current_site
from django.utils.encoding import force_bytes, force_text
from django.utils.http import urlsafe_base64_encode, urlsafe_base64_decode
from django.template.loader import render_to_string
from rest_framework.response import Response
from rest_framework import status
from .serializers import CreateRequestSerializer, RequestTutorSerializer, RequestSerializer
from rest_framework.decorators import api_view, permission_classes
from rest_framework.permissions import AllowAny
from .models import Request
from rest_framework.authtoken.models import Token
from django.contrib.auth import get_user_model


@api_view(['POST', ])
@permission_classes([AllowAny, ])
def submit_request(request):

    serializer = CreateRequestSerializer(data=request.data)
    if serializer.is_valid(raise_exception=True):
        request = serializer.save()}
        return Response(serializer.data, status=status.HTTP_201_CREATED)
    else:
        return Response('request couldnt be created', status=status.HTTP_501_NOT_IMPLEMENTED)


'''view to list all requests that a tutor has received'''
@api_view(['GET', ])
@permission_classes([AllowAny, ])
def list_requests_tutor(request):
    id = request.GET.get('id')
    requests = Request.objects.filter(tutor__id=id)
    serializer = RequestTutorSerializer(requests, many=True)
    return Response(serializer.data)


'''view to list all requests that a tutor has received'''
@api_view(['GET', ])
@permission_classes([AllowAny, ])
def list_user_requests(request):
    id = request.GET.get('id')
    requests = Request.objects.filter(requester__id=id)
    serializer = RequestSerializer(requests, many=True)
    return Response(serializer.data)


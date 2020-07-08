from django.shortcuts import render
from django.db.models import Q
from django.contrib.auth import get_user_model
from rest_framework.response import Response
from rest_framework.status import HTTP_200_OK, HTTP_400_BAD_REQUEST
from rest_framework.views import APIView
from .serializers import UserLoginSerializer
from rest_framework.permissions import AllowAny

# Create your views here.
UserLogin = get_user_model()

class UserLoginView(APIView):
    serializer_class = UserLoginSerializer

    def post(self, request, *args, **kwargs):
        data = request.data
        serializer = UserLoginSerializer(data= data)
        if serializer.is_valid(raise_exception=True):
            new_data =serializer.data
            return Response(new_data, status=HTTP_200_OK)
        return  Response(serializer.errors, status=HTTP_400_BAD_REQUEST)

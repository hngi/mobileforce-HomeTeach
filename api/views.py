from django.shortcuts import render

# Create your views here.
from rest_framework.response import Response
from rest_framework import status, generics
from rest_framework.permissions import IsAuthenticatedOrWriteOnly
from api.serializers import CustomUserSerializer



class CustomUserList(generics.ListCreateAPIView):
    permission_classes = (IsAuthenticatedOrWriteOnly,)
    serializer_class = CustomUserSerializer

    def post(self, request, format=None):
        serializer = CustomUserSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

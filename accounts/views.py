from rest_framework.response import Response
from rest_framework import status
from rest_framework.decorators import api_view, permission_classes
from rest_framework.permissions import AllowAny
from .serializers import RegistrationSerializer
from rest_framework.authtoken.models import Token
from django.shortcuts import render
from django.db.models import Q
from django.contrib.auth import get_user_model
from rest_framework.status import HTTP_200_OK, HTTP_400_BAD_REQUEST
from rest_framework.views import APIView
from .serializers import UserLoginSerializer
from rest_framework import parsers, renderers



UserLogin = get_user_model()

class UserLoginView(APIView):
    permission_classes = [AllowAny]
    serializer_class = UserLoginSerializer
    def post(self, request, *args, **kwargs):
        data = request.data
        serializer = UserLoginSerializer(data = data)
        if serializer.is_valid(raise_exception=True):
            account = serializer.save()
            data =serializer.data
            return Response(data, status=HTTP_200_OK)
        return  Response(serializer.errors, status=HTTP_400_BAD_REQUEST)

# class UserLoginView(APIView):
#     throttle_classes = ()
#     permission_classes = ()
#     parser_classes = (
#         parsers.FormParser,
#         parsers.MultiPartParser,
#         parsers.JSONParser,
#     )

#     renderer_classes = (renderers.JSONRenderer,)

#     def post(self, request):
#         serializer = AuthCustomTokenSerializer(data=request.data)
#         serializer.is_valid(raise_exception=True)
#         user = serializer.validated_data['user']
#         token, created = Token.objects.get_or_create(user=user)

#         content = {
#             'token': token.key,
#         }

#         return Response(content)


@api_view(['POST', ])
@permission_classes([AllowAny, ])
def api_register_view(request):
    serializer = RegistrationSerializer(data=request.data)
    data = {}
    if serializer.is_valid():
        account = serializer.save()
        data['token'] = Token.objects.get(user=account).key
        data['user'] = serializer.data
    else:
        data['errors'] = serializer.errors
    return Response(data, status=status.HTTP_201_CREATED)
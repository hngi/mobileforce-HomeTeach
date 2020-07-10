from rest_framework.response import Response
from rest_framework import status
from .serializers import CreateRequestSerializer, RequestTutorSerializer, RequestSerializer
from rest_framework.decorators import api_view, permission_classes
from rest_framework.permissions import AllowAny
from .models import Request
from api.permissions import IsOwnerOrReadOnly, IsAdminUserOrReadOnly, IsSameUserAllowEditionOrReadOnly
from api.serializers import CustomUserSerializer, ProfileSerializer
from api.models import Profile
from accounts.models import CustomUser 
from rest_framework.parsers import FileUploadParser
from rest_framework import viewsets, mixins, permissions
from rest_framework.authtoken.models import Token
from django.contrib.auth import get_user_model


@api_view(['POST', ])
@permission_classes([AllowAny, ])
def submit_request(request):

    serializer = CreateRequestSerializer(data=request.data)
    if serializer.is_valid(raise_exception=True):
        serializer.save()
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



class CustomUserViewSet(viewsets.ModelViewSet):
    """
    This viewset automatically provides `list` and `detail` actions.
    """
    queryset = CustomUser.objects.all()
    serializer_class = CustomUserSerializer
    permission_classes = (permissions.AllowAny,
                          IsSameUserAllowEditionOrReadOnly,)
    parser_class = (FileUploadParser,)

class ProfileViewSet(mixins.ListModelMixin,
                     mixins.RetrieveModelMixin,
                     mixins.UpdateModelMixin,
                     viewsets.GenericViewSet):
    """
    This viewset automatically provides `list`, `create`, `retrieve`,
    `update` and `destroy` actions.
    """
    queryset = Profile.objects.all()
    serializer_class = ProfileSerializer
    permission_classes = (permissions.AllowAny,
                          IsOwnerOrReadOnly,)

class UserProfileViewSet(mixins.RetrieveModelMixin,
                     viewsets.GenericViewSet):
    """
    This viewset automatically provides `list`, `create`, `retrieve`,
    `update` and `destroy` actions.
    """
    queryset = Profile.objects.all()
    serializer_class = ProfileSerializer
    permission_classes = (permissions.AllowAny,
                          IsOwnerOrReadOnly,)
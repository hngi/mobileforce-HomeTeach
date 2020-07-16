from rest_framework.response import Response
from rest_framework import status
from .serializers import CreateRequestSerializer, RequestTutorSerializer, RequestSerializer,TopTutorSerializer
from rest_framework.decorators import api_view, permission_classes
from rest_framework.permissions import AllowAny
from .models import Request,Rating
from .permissions import IsOwnerOrReadOnly, IsAdminUserOrReadOnly, IsSameUserAllowEditionOrReadOnly
from .serializers import CustomUserSerializer, ProfileSerializer, TutorProfileSerializer, StudentProfileSerializer, RatingsSerializer,TopTutorSerializer
from .models import Profile
from accounts.models import CustomUser 
from rest_framework.parsers import FileUploadParser
from rest_framework import viewsets, mixins, permissions
from rest_framework.authtoken.models import Token
from django.contrib.auth import get_user_model
from django_filters import rest_framework as filters
from rest_framework.filters import SearchFilter,OrderingFilter
from django.contrib.auth import get_user_model
from django.shortcuts import get_object_or_404


@api_view(['POST', ])
@permission_classes([AllowAny, ])
def submit_request(request):

    serializer = CreateRequestSerializer(data=request.data)
    if serializer.is_valid(raise_exception=True):
        data = serializer.save()
        serialized_data = serializer.data
        serialized_data['id'] = data.id
        user = data.tutor.full_name
        message = f'Your request to {user} was succesfully sent!'
        return Response({'status':'successful','sent':True, 'message':message}, status=status.HTTP_201_CREATED)
    else:
        return Response({'message':'sorry, your request couldnt be sent...', 'sent':False}, status=status.HTTP_501_NOT_IMPLEMENTED)


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
    permission_classes = (AllowAny,
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
    permission_classes = (AllowAny,
                          IsOwnerOrReadOnly,)

  

class TutorProfileViewSet(mixins.ListModelMixin,
                          mixins.RetrieveModelMixin,
                          viewsets.GenericViewSet):
    """
    This viewset automatically provides `list`, `create`, `retrieve`,
    `update` and `destroy` actions.
    """
    queryset = Profile.objects.filter(user__is_tutor=True)
    serializer_class = TutorProfileSerializer
    permission_classes = (AllowAny,
                          IsOwnerOrReadOnly,)
    filter_backends = (filters.DjangoFilterBackend,)
    filter_fields = ('field','major_course','state',)
    ordering = ('-full_name',)
  
 
class StudentProfileViewSet(mixins.ListModelMixin,
                            mixins.RetrieveModelMixin,
                            viewsets.GenericViewSet):
    """
    This viewset automatically provides `list`, `create`, `retrieve`,
    `update` and `destroy` actions.
    """

    queryset = Profile.objects.filter(user__is_tutor=False)
    serializer_class = StudentProfileSerializer
    permission_classes = (AllowAny,
                          IsOwnerOrReadOnly,)


@api_view(['POST', ])
@permission_classes([AllowAny, ])
def rate_tutor(request):
    data = request.data

    serializer = RatingsSerializer(data=data)
    if serializer.is_valid(raise_exception=True):
        serializer.save()
        return Response({f'you have rated this tutor {serializer.data.get("rate")} stars'}, status=status.HTTP_201_CREATED)
    return Response(status=status.HTTP_501_NOT_IMPLEMENTED)

User = get_user_model()
@api_view(['GET', ])
@permission_classes([AllowAny, ])
def top_tutors(request):
    query = Profile.objects.filter(user__is_tutor=True)
    serializer = TopTutorSerializer(query)
    return Response(serializer.data)

# Create your views here.
from rest_framework.response import Response
from rest_framework import status, generics
from api.permissions import IsOwnerOrReadOnly, IsAdminUserOrReadOnly, IsSameUserAllowEditionOrReadOnly
from api.serializers import CustomUserSerializer, ProfileSerializer
from api.models import Profile
from accounts.models import CustomUser 
 
from rest_framework import viewsets, mixins, permissions



class CustomUserViewSet(viewsets.ModelViewSet):
    """
    This viewset automatically provides `list` and `detail` actions.
    """
    queryset = CustomUser.objects.all()
    serializer_class = CustomUserSerializer
    permission_classes = (permissions.IsAuthenticatedOrReadOnly,
                          IsSameUserAllowEditionOrReadOnly,)

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
    permission_classes = (permissions.IsAuthenticatedOrReadOnly,
                          IsOwnerOrReadOnly,)



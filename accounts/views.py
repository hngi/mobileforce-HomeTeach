from rest_framework.response import Response
from rest_framework import status
from rest_framework.decorators import api_view, permission_classes
from rest_framework.permissions import AllowAny
from .serializers import RegistrationSerializer
from rest_framework.authtoken.models import Token



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
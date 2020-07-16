from django.shortcuts import render
from django.http import JsonResponse

from rest_framework.decorators import api_view,authentication_classes,permission_classes
from rest_framework.response import Response
from .serializers import ScheduleSerializer

from .models import Schedule


@api_view(['GET'])
@authentication_classes([])
@permission_classes([])
def scheduleList(request):
    query = Schedule.objects.all().order_by('class_date')
    serializer = ScheduleSerializer(query, many=True)
    return Response(serializer.data)

@api_view(['GET'])
@authentication_classes([])
@permission_classes([])
def scheduleDetail(request, pk):
    query = Schedule.objects.get(id=pk)
    serializer = ScheduleSerializer(query, many=False)
    return Response(serializer.data)

@api_view(['POST'])
@authentication_classes([])
@permission_classes([])
def scheduleCreate(request):
    serializer = ScheduleSerializer(data=request.data)

    if serializer.is_valid():
        serializer.save()
    return Response(serializer.data)

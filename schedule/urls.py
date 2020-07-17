from django.urls import path
from . import views

urlpatterns = [
    path('class-list/',views.scheduleList,name='class-schedule'),
   # path('class-list/<str:pk>',views.scheduleDetail,name='class-list'),
    path('class-create/',views.scheduleCreate,name='class-create'),
]

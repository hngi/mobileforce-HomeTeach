from django.urls import path
from . import views

urlpatterns = [
    path('register/', views.api_register_view)
]
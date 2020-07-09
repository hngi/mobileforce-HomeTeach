from django.urls import path
from . import views

urlpatterns = [
    path('submit_request/', views.submit_request),
    path('tutor_requests/', views.list_requests_tutor),
    path('user_requests/', views.list_user_requests),
]




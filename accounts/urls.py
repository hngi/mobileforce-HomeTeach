from django.urls import path
from . import views

urlpatterns = [
    path('register/', views.api_register_view),
    path('login/', views.UserLoginView.as_view(), name='login'),
    path('activate/<slug:uidb64>/<slug:token>', views.activate, name='activate'),
]
from django.urls import path, include
from . import views
from accounts.views import reset_password_confirm, reset_password_request_token, reset_password_validate_token
import django_rest_passwordreset

urlpatterns = [
    path('register/', views.api_register_view),
    path('login/', views.UserLoginView.as_view(), name='login'),
    path('logout/', views.UserLogoutView.as_view(), name='logout'),
    path('activate/<slug:uidb64>/<slug:token>', views.activate, name='activate'),
    
    path('validate-reset-token/', reset_password_validate_token, name='reset-password-validate'),
    path('confirming-reset/', reset_password_confirm, name='reset-password-confirm'),
    path('password-reset/', reset_password_request_token, name='reset-password-request'),
]
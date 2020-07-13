from django.urls import path, include
from django.contrib.auth import views
from django_rest_password.views import ResetPasswordConfirm, ResetPasswordValidateToken, ResetPasswordRequestToken

urlpatterns = [
    path('register/', views.api_register_view),
    path('login/', views.UserLoginView.as_view(), name='login'),
    path('logout/', views.UserLogoutView.as_view(), name='logout'),
    path('activate/<slug:uidb64>/<slug:token>', views.activate, name='activate'),

    # The django-rest-passwordreset urls to request a token and confirm pw-reset
    path('validate_token/', views.ResetPasswordValidateToken.as_view(), name='reset-password-validate'),
    path('confirm', views.ResetPasswordConfirm.as_view(), name='reset-password-confirm'),
    path('password_reset',  ResetPasswordRequestToken.as_view(), name='reset-password-request'),
    
    # password_reset/confirm/
    # password_reset/validate_token/
]
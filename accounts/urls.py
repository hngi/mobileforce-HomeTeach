from django.urls import path, include, re_path
from . import views
from django.conf.urls import url 
from rest_auth.views import PasswordResetView, PasswordChangeView, PasswordResetConfirmView

urlpatterns = [
    path('register/', views.api_register_view),
    path('login/', views.UserLoginView.as_view(), name='login'),
    path('logout/', views.UserLogoutView.as_view(), name='logout'),
    path('activate/<slug:uidb64>/<slug:token>', views.activate, name='activate'),

    # The django-rest-passwordreset urls to request a token and confirm pw-reset
    #path('password-reset/', include('django_rest_passwordreset.urls', namespace='password_reset')),
    # password_reset/confirm/
    # password_reset/validate_token/

     # password reset
    path('', include('allauth.urls')),
   
    
    

    url(r'^', include('django.contrib.auth.urls')),
    path('rest-auth/', PasswordResetView.as_view()),
    path('rest-auth/', PasswordResetConfirmView.as_view()),
    path('rest-auth/', PasswordChangeView.as_view()),
]

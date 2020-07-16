from django.urls import path, include
from . import views
#from accounts.views import reset_password_confirm, reset_password_request_token, reset_password_validate_token
#import django_rest_passwordreset
from django.conf.urls import url


urlpatterns = [
    path('register/', views.api_register_view),
    path('login/', views.UserLoginView.as_view(), name='login'),
    path('logout/', views.UserLogoutView.as_view(), name='logout'),
    path('activate/<slug:uidb64>/<slug:token>', views.activate, name='activate'),

    # The django-rest-passwordreset urls to request a token and confirm pw-reset
    #path('password-reset/', include('django_rest_passwordreset.urls', namespace='password_reset')),
    # password_reset/confirm/
    # password_reset/validate_token/

    #path('api/password_reset', include("django_rest_passwordreset.urls", namespace='password-reset')),
    url('^', include('django.contrib.auth.urls')),
    path('rest-auth/', include('rest_auth.urls')),

    ]
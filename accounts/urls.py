from django.urls import path, include
from . import views
from .views import UserLoginView

urlpatterns = [
    path('register/', views.api_register_view),
    path('login/', UserLoginView.as_view(), name = 'login'),
    path('activate/<slug:uidb64>/<slug:token>', views.activate, name='activate'),

    # The django-rest-passwordreset urls to request a token and confirm pw-reset
    path('password_reset/', include('django_rest_passwordreset.urls', namespace='password_reset')),
    # password_reset/confirm/
    # password_reset/validate_token/
]
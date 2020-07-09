from django.urls import path
from . import views
from .views import UserLoginView

urlpatterns = [
    path('register/', views.api_register_view),
    path('login/', UserLoginView.as_view(), name = 'login'),
    path('activate/<slug:uidb64>/<slug:token>', views.activate, name='activate'),
]
from django.urls import path
from .views import InitializeTransacView


urlpatterns = [
	path('initialize-transac/', InitializeTransacView.as_view(), name='initialize-trans'),
]
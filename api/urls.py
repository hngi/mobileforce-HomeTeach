from django.urls import path 
from api.views import CustomUserList

urlpatterns = [
	path('profile/', CustomUserList.as_view()),
]


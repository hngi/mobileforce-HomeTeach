from django.urls import path 
from api.views import CustomUserViewSet, ProfileViewSet

from rest_framework.routers import DefaultRouter

router = DefaultRouter()
router.register(r'users', CustomUserViewSet)
router.register(r'profiles', ProfileViewSet)
urlpatterns = router.urls


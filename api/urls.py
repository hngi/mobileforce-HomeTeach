from django.urls import path
from api.views import CustomUserViewSet, ProfileViewSet, UserProfileViewSet
from rest_framework.routers import DefaultRouter
from . import views

router = DefaultRouter()
router.register(r'users', CustomUserViewSet)
router.register(r'profiles', ProfileViewSet)
router.register(r'tutorprofiles', UserProfileViewSet)

urlpatterns = [
    path('submit_request/', views.submit_request),
    path('tutor_requests/', views.list_requests_tutor),
    path('user_requests/', views.list_user_requests),
]

urlpatterns += router.urls
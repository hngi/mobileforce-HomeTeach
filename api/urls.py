from django.urls import path
from api.views import (CustomUserViewSet, ProfileViewSet,
                TutorProfileViewSet, StudentProfileViewSet)
from rest_framework.routers import DefaultRouter
from . import views


router = DefaultRouter()
router.register('users', CustomUserViewSet)
router.register('profiles', ProfileViewSet)
router.register('tutor-profiles', TutorProfileViewSet)
router.register('student-profiles', StudentProfileViewSet)

urlpatterns = [
    path('submit-request/', views.submit_request),
    path('tutor-requests/', views.list_requests_tutor),
    path('user-requests/', views.list_user_requests),
    path('request-action/', views.request_action),
    path('tutor-classes/', views.get_tutor_classes),
    path('student-classes/', views.get_student_classes),
    path('tutor-classes-requests/', views.get_tutor_classes_requests),
    path('student-classes-requests/', views.get_student_classes_requests),
    path('rate-tutor/', views.rate_tutor),
    path('top-tutors/', views.top_tutors),
    path('add-favourites/', views.add_favourites),
    path('bank-info/', views.BankInfoView),
    path('bank-info-user/', views.BankInfoByUser),
    path('bank-info/<int:pk>/', views.bank_info_by_id),
    path('credit-cards/', views.card_info),
    path('user-card-details/', views.card_info_by_user),
    path('card-by-id/<int:pk>/', views.card_info_by_id),
    path('verify-transaction/', views.VerificationView.as_view()),
    path('verify-transaction/<str:user>/', views.VerificationDetailView.as_view()),
    #path('initialize-transaction/', views.InitializeTransactionView.as_view()),
    path('user-wallet/<str:user>/', views.UserWalletView.as_view()),
]

urlpatterns += router.urls

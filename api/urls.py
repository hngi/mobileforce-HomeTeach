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
    #path('initialize-transaction/', views.InitializeTransactionView.as_view()),
    path('user-wallet/', views.UserWalletView.as_view()),
]

urlpatterns += router.urls

from django.urls import path
from .views import ContactCreateView

urlpatterns = [
    path('course-confirmation/',ContactCreateView.as_view()),
]
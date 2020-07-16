from django.urls import path
from .views import InitializeTransactionView, CreateCustomerView


urlpatterns = [
	path('initialize-transaction/', InitializeTransactionView.as_view(), name='InitializeTransaction'),
	path('create-customer/', CreateCustomerView.as_view(), name='CreateCustomer'),
]
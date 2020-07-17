from django.shortcuts import render
from rest_framework.response import Response
from root.settings import PAYSTACK_AUTHORIZATION_KEY
from pypaystack import Transaction, Customer
from .serializers import TransactionsSerializer
from rest_framework.views import APIView
from rest_framework import permissions, status


class InitializeTransactionView(APIView):
	permission_classes = (permissions.AllowAny, )

	def post(self, request, *args, **kwargs):
		serializer = TransactionsSerializer(data=request.data)
		transaction = Transaction(authorization_key=PAYSTACK_AUTHORIZATION_KEY)
		if serializer.is_valid():
			email = serializer.validated_data['email']
			amount = serializer.validated_data['amount']

			payload = transaction.initialize(email, amount)

			return Response(payload, status=status.HTTP_200_OK)

		else:
			return Response(serializer.errors, status=status.HTTP_401_UNAUTHORIZED)

		

class CreateCustomerView(APIView):
	permission_classes = (permissions.AllowAny, )

	def post(self, request, *args, **kwargs):
		serializer = TransactionsSerializer(data=request.data)
		customer = Customer(authorization_key=PAYSTACK_AUTHORIZATION_KEY)
		if serializer.is_valid():
			email = serializer.validated_data['email']

			payload = customer.create(email)

			return Response(payload, status=status.HTTP_200_OK)

		else:
			return Response(serializer.errors, status=status.HTTP_401_UNAUTHORIZED)


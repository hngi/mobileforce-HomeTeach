from django.shortcuts import render
from rest_framework.response import Response
from root.settings import PAYSTACK_AUTHORIZATION_KEY
from pypaystack import Transaction
from .serializers import InitializeTransacSerializer
from rest_framework.views import APIView


class InitializeTransacView(APIView):

	def post(request, *args, **kwargs):
		serializer = InitializeTransacSerializer(data=request.data)
		transaction = Transaction(authorization_key=PAYSTACK_AUTHORIZATION_KEY)
		if serializer.is_valid():
			email = serializer.validated_data['email']
			amount = serializer.validated_data['amount']
			reference = serializer.validated_data['ref']

			payload = transaction.initialize(email, amount, reference)

			return Response(payload)


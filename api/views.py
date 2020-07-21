import requests
from django.http import Http404
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework.decorators import api_view, permission_classes
from rest_framework.permissions import AllowAny, IsAuthenticatedOrReadOnly, IsAuthenticated
from .permissions import IsOwnerOrReadOnly, IsAdminUserOrReadOnly, IsSameUserAllowEditionOrReadOnly
from accounts.models import CustomUser 
from rest_framework.parsers import FileUploadParser, MultiPartParser, FormParser
from rest_framework import viewsets, mixins, permissions, status
from rest_framework.authtoken.models import Token
from django.contrib.auth import get_user_model
from django_filters import rest_framework as filters
from rest_framework.filters import SearchFilter,OrderingFilter
from django.shortcuts import get_object_or_404
from cardvalidator import formatter, luhn
from .utility.encryption_util import *
from .serializers import (BankInfoSerializer, CreditCardInfoSerializer, 
						VerificationSerializer, CreateRequestSerializer, 
						RequestTutorSerializer, RequestSerializer, TopTutorSerializer,
						CustomUserSerializer, ProfileSerializer, 
						TutorProfileSerializer, StudentProfileSerializer, 
						RatingsSerializer, TopTutorSerializer, UserWalletSerializer)
from .models import (BankInfo, CreditCardInfo, UserWallet,
		Verification, Profile, Request,Rating, Verification)
from root.settings import PAYSTACK_AUTHORIZATION_KEY
from pypaystack import Transaction



@api_view(['POST', ])
@permission_classes([AllowAny, ])
def submit_request(request):

	serializer = CreateRequestSerializer(data=request.data)
	if serializer.is_valid(raise_exception=True):
		data = serializer.save()
		serialized_data = serializer.data
		serialized_data['id'] = data.id
		user = data.tutor.full_name
		message = f'Your request to {user} was succesfully sent!'
		return Response({'status':'successful','sent':True, 'message':message}, status=status.HTTP_201_CREATED)
	else:
		return Response({'message':'sorry, your request couldnt be sent...', 'sent':False}, status=status.HTTP_501_NOT_IMPLEMENTED)


'''view to list all requests that a tutor has received'''
@api_view(['GET', ])
@permission_classes([AllowAny, ])
def list_requests_tutor(request):
	id = request.GET.get('id')
	requests = Request.objects.filter(tutor__id=id)
	serializer = RequestTutorSerializer(requests, many=True)
	return Response(serializer.data)


'''view to list all requests that a tutor has received'''
@api_view(['GET', ])
@permission_classes([AllowAny, ])
def list_user_requests(request):
	id = request.GET.get('id')
	requests = Request.objects.filter(requester__id=id)
	serializer = RequestSerializer(requests, many=True)
	return Response(serializer.data)



class CustomUserViewSet(viewsets.ModelViewSet):
	"""
	This viewset automatically provides `list` and `detail` actions.
	"""
	queryset = CustomUser.objects.all()
	serializer_class = CustomUserSerializer
	permission_classes = (AllowAny,
						  IsSameUserAllowEditionOrReadOnly,)
	parser_class = (FileUploadParser,)

class ProfileViewSet(mixins.ListModelMixin,
					 mixins.RetrieveModelMixin,
					 mixins.UpdateModelMixin,
					 viewsets.GenericViewSet):
	"""
	This viewset automatically provides `list`, `create`, `retrieve`,
	`update` and `destroy` actions.
	"""
	queryset = Profile.objects.all()
	serializer_class = ProfileSerializer
	permission_classes = (AllowAny,
						  IsOwnerOrReadOnly,)

  

class TutorProfileViewSet(mixins.ListModelMixin,
						  mixins.RetrieveModelMixin,
						  mixins.UpdateModelMixin,
						  viewsets.GenericViewSet):
	"""
	This viewset automatically provides `list`, `create`, `retrieve`,
	`update` and `destroy` actions.
	"""
	parser_classes = (MultiPartParser, FormParser,)
	queryset = Profile.objects.filter(user__is_tutor=True)
	serializer_class = TutorProfileSerializer
	permission_classes = (AllowAny,
						  IsOwnerOrReadOnly,)
	filter_backends = (filters.DjangoFilterBackend,)
	filter_fields = ('field','major_course','state',)
	ordering = ('-full_name',)
  
 
class StudentProfileViewSet(mixins.ListModelMixin,
							mixins.RetrieveModelMixin,
							mixins.UpdateModelMixin,
							viewsets.GenericViewSet):
	"""
	This viewset automatically provides `list`, `create`, `retrieve`,
	`update` and `destroy` actions.
	"""
	parser_classes = (MultiPartParser, FormParser,)
	queryset = Profile.objects.filter(user__is_tutor=False)
	serializer_class = StudentProfileSerializer
	permission_classes = (AllowAny,
						  IsOwnerOrReadOnly,)


@api_view(['POST', ])
@permission_classes([AllowAny, ])
def rate_tutor(request):
	data = request.data

	serializer = RatingsSerializer(data=data)
	if serializer.is_valid(raise_exception=True):
		serializer.save()
		return Response({f'you have rated this tutor {serializer.data.get("rate")} stars'}, status=status.HTTP_201_CREATED)
	return Response(status=status.HTTP_501_NOT_IMPLEMENTED)

User = get_user_model()
@api_view(['GET', ])
@permission_classes([AllowAny, ])
def top_tutors(request):
	query = Profile.objects.filter(user__is_tutor=True)
	serializer = TopTutorSerializer(query)
	return Response(serializer.data)




#//////////////////////////////////////////Credit card/////////////////////////////////////////
# Card-Validator
# cryptography
@api_view(['POST' ])
@permission_classes([AllowAny, ])
def card_info_by_user(request):
	"""
	This view returns details of all the credit card register to a particular user
	"""
	user = request.data['user']
	card_details = CreditCardInfo.objects.filter(user=user)
	serializer = CreditCardInfoSerializer(card_details, many=True)

	user_card_details = []
	for card_detail in serializer.data:
		d_id = card_detail['id']
		user, card_holder_name, card_number, cvv = card_detail['user'], card_detail['card_holder_name'], card_detail['card_number'], card_detail['cvv']
		expiry_month, expiry_year = card_detail['expiry_month'], card_detail['expiry_year']
		parsed_data = {"id": d_id, "user" : user, "card_holder_name": card_holder_name, "card_number": decrypt(card_number), "cvv": decrypt(cvv),
		"expiry_month": expiry_month, "expiry_year": expiry_year}
		user_card_details.append(parsed_data)

	
	# return Response(serializer.data)
	return Response(user_card_details)

@api_view(['POST', 'GET'])
@permission_classes([AllowAny, ])
def card_info(request):
	"""
	This view gets all credit card details with the sensitive data encrypted 
	Also saves new credit card details and encrypts sensitive info
	"""
	if request.method == 'GET':
		card_details = CreditCardInfo.objects.all()
		serializer = CreditCardInfoSerializer(card_details, many=True)
		return Response(serializer.data)

	elif request.method == 'POST':
		data = request.data
		user, card_holder_name, card_number, cvv = data['user'], data['card_holder_name'], data['card_number'], data['cvv']
		expiry_month, expiry_year = data['expiry_month'], data['expiry_year']
		if luhn.is_valid(card_number):
			# card_number = encrypt(card_number)
			parsed_data = {"user" : user, "card_holder_name": card_holder_name, "card_number": encrypt(card_number), "cvv": encrypt(cvv),
			"expiry_month": expiry_month, "expiry_year": expiry_year}
			serializer = CreditCardInfoSerializer(data = parsed_data)

			if serializer.is_valid():
				serializer.save()
				return Response(serializer.data)
			return Response(serializer.errors)
		else:
			return Response('Card number is not valid')
		
@api_view(['GET', 'PUT', 'DELETE'])
@permission_classes([AllowAny, ])
def card_info_by_id(request, pk):
	"""
	This view gets credit card detail by id, updates and deletes it
	"""
	try:
		card_detail = CreditCardInfo.objects.get(pk=pk)

	except CreditCardInfo.DoesNotExist:
		return Response('There are no Credit card details for this id')

	if request.method == 'GET':
		serializer = CreditCardInfoSerializer(card_detail)
		card_detail = serializer.data
		d_id = card_detail['id']
		user, card_holder_name, card_number, cvv = card_detail['user'], card_detail['card_holder_name'], card_detail['card_number'], card_detail['cvv']
		expiry_month, expiry_year = card_detail['expiry_month'], card_detail['expiry_year']
		parsed_data = {"id": d_id, "user" : user, "card_holder_name": card_holder_name, "card_number": decrypt(card_number), "cvv": decrypt(cvv),
		"expiry_month": expiry_month, "expiry_year": expiry_year}
		return Response(parsed_data)

	elif request.method == 'PUT':
		data = request.data
		user, card_holder_name, card_number, cvv = data['user'], data['card_holder_name'], data['card_number'], data['cvv']
		expiry_month, expiry_year = data['expiry_month'], data['expiry_year']
		if luhn.is_valid(card_number):
			# card_number = encrypt(card_number)
			parsed_data = {"user" : user, "card_holder_name": card_holder_name, "card_number": encrypt(card_number), "cvv": encrypt(cvv),
			"expiry_month": expiry_month, "expiry_year": expiry_year}
			serializer = CreditCardInfoSerializer(card_detail, data=parsed_data)

			if serializer.is_valid():
				serializer.save()
				return Response(serializer.data)
			return Response(serializer.errors)
		else:
			return Response('Card number is not valid')


	elif request.method == 'DELETE':
		card_detail.delete()
		return Response('Card details deleted')        




#/////////////////////////////////////////Credit card /////////////////////////////////////




#//////////////////////////////////////Bank Details/////////////////////////////////////////

@api_view(['GET', 'PUT', 'DELETE'])
@permission_classes([AllowAny, ])
def bank_info_by_id(request, pk):
	"""
	This view gets all bank details by id, updates and deletes it
	"""
	try:
		Bank_detail = BankInfo.objects.get(pk=pk)

	except BankInfo.DoesNotExist:
		return Response('There are no bank details for this id')

	if request.method == 'GET':
		serializer = BankInfoSerializer(Bank_detail)
		return Response(serializer.data)

	elif request.method == 'PUT':
		serializer = BankInfoSerializer(Bank_detail, data=request.data)
		if serializer.is_valid():
			serializer.save()
			return Response(serializer.data)
		return Response(serializer.errors)

	elif request.method == 'DELETE':
		Bank_detail.delete()
		return Response('Bank details deleted')



@api_view(['POST' ])
@permission_classes([AllowAny, ])
def  BankInfoByUser(request):
	"""
	This view gets all Bank details saved to a particular user
	"""
	user = request.data['user']
	Bank_d = BankInfo.objects.filter(user=user)
	serializer = BankInfoSerializer(Bank_d, many=True)
	return Response(serializer.data)

@api_view(['POST', 'GET'])
@permission_classes([AllowAny, ])
def BankInfoView(request):
	"""
	This view gets all bank details in the db
	also saves new bank details
	"""

	if request.method == 'GET':
		bank_details = BankInfo.objects.all()
		serializer = BankInfoSerializer(bank_details, many=True)
		return Response(serializer.data)

	elif request.method == 'POST':
		serializer = BankInfoSerializer(data = request.data)

		if serializer.is_valid():
			serializer.save()
			return Response(serializer.data)
		return Response(serializer.errors)

class VerifyTransactionView(APIView):
	permission_classes = (AllowAny,)

	def get_object(self, user):
		try:
			return Verification.objects.filter(user=user)
		except Verification.DoesNotExist:
			raise Http404

	def get(self, request, format=None):
		user = request.data['user']
		verification_details = self.get_object(user)
		serializer = VerificationSerializer(verification_details, many=True)
		return Response(serializer.data)


	def post(self, request, *args, **kwargs):
		url = "https://api.paystack.co/transaction/verify/"
		serializer = VerificationSerializer(data=request.data)
		if serializer.is_valid():
			user = serializer.validated_data['user']
			reference = serializer.validated_data['reference']
			r = requests.get(url+reference,
							headers={'Authorization': 'Bearer {}'.format(PAYSTACK_AUTHORIZATION_KEY)})
			json = r.json()
			
			#auth = json['data']['authorization']['authorization_code']
			try:
				amount = json['data']['amount']
				email = json['data']['customer']['email']
				auth = json['data']['authorization']['authorization_code']
				'''
				obj = UserWallet.objects.get(user=request.user)
				obj.= auth
				obj.amount = amount
				obj.email = email
				obj.save()
				'''
				
				serializer.save(amount=amount, email=email, authorization_code=auth)	
			except:	
				serializer.save(amount=amount, email=email)
				instance = UserWallet.objects.create(user=user, total_balance=amount, available_balance=amount)
				instance.save()		
			return Response(json, status=status.HTTP_200_OK)
		return Response(status=status.HTTP_401_UNAUTHORIZED)

class InitializeTransactionView(APIView):
	permission_classes = (AllowAny,)

	def post(self, request, *args, **kwargs):
		transaction = Transaction(authorization_key=PAYSTACK_AUTHORIZATION_KEY)
		serializer = VerificationSerializer(data=request.data)
		if serializer.is_valid():
			email = serializer.validated_data['email']
			amount = serializer.validated_data['amount']

			payload = transaction.initialize(email, amount)
			
			return Response(payload, status=status.HTTP_200_OK)
		return Response(serializer.errors, status=status.HTTP_401_UNAUTHORIZED)

'''
class UserWalletView(APIView):
	permission_classes = (AllowAny, )

	""" Retrieve wallet statement for a user instance """

	def get_object(self, user):
		try:
			return UserWallet.objects.filter(user=user)
		except UserWallet.DoesNotExist:
			raise Http404

	def get(self, request, format=None):
		user = request.data['user']
		user_wallet = self.get_object(user)
		serializer = UserWalletSerializer(user_wallet)
		
		return Response(serializer.data)
'''


			





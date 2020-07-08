from django.conf import settings
from rest_framework import serializers
from rest_framework.validators import UniqueValidator
from django.contrib.auth import get_user_model, authenticate
from rest_framework.serializers import ModelSerializer,CharField,EmailField,ModelSerializer,SerializerMethodField,ValidationError
from django.db import models
from django.db.models import Q
from accounts.models import CustomUser
from rest_framework import exceptions
from django.shortcuts import get_object_or_404
from rest_framework.authtoken.models import Token



User = get_user_model()

class RegistrationSerializer(serializers.ModelSerializer):
    email = serializers.EmailField(required=True, validators=[UniqueValidator(queryset=User.objects.all())])


    class Meta:
        model = User
        fields = ['email','is_tutor', 'password','full_name', 'phone_number']
        extra_kwargs = {
            'password': {'write_only': True}
        }

    def save(self):
        password = self.validated_data['password']
        phone_number = self.validated_data['phone_number']


        if len(phone_number) < 6:
            raise serializers.ValidationError({'phone number must be greater than 5 digits'})

        user = User.objects.create_user(

            email= self.validated_data['email'],
            full_name= self.validated_data['full_name'],
            phone_number = self.validated_data['phone_number']

        )
        user.is_tutor = self.validated_data['is_tutor']
        # user.full_name = self.validated_data['full_name']
        # user.phone_number = self.validated_data['phone_number']

        user.set_password(password)
        user.save()
        return user


User = get_user_model()
class UserLoginSerializer(ModelSerializer):
    token = CharField(max_length = 100, read_only = True)
    email = EmailField(max_length = 100)

    class Meta:
        model = CustomUser  
        fields = [ 
        'email',
        'password',
        "token",
         ]

        extra_kwargs = {"password": {"write_only":True}}

    def validate(self, data):
        user_obj = None
        email = data.get("email")
        password = data["password"]
        #token = Token.objects.get(user=data).key 
        if not email:
            raise ValidationError("Email cannot be blank")
        user = CustomUser.objects.filter(email = email)
        
        
        
        if user.exists() and user.count==1:
           
            user_obj = user.First()
        else:
            raise ValidationError("Invalid username or Password")

        if user_obj:
            if not user_obj.check_password(password):
                raise ValidationError("invalid password")

        return data

from django.conf import settings
from rest_framework import serializers
from rest_framework.validators import UniqueValidator
from django.contrib.auth import get_user_model, authenticate
from rest_framework.serializers import ModelSerializer,CharField,EmailField,SerializerMethodField,ValidationError
from django.db import models
from django.db.models import Q
from .models import CustomUser
from api.serializers import ProfileSerializer
from api.models import Profile
from rest_framework import exceptions
from django.shortcuts import get_object_or_404
from rest_framework.authtoken.models import Token


User = get_user_model()

class RegistrationSerializer(serializers.ModelSerializer):
    email = serializers.EmailField(required=True, validators=[UniqueValidator(queryset=User.objects.all())])


    class Meta:
        model = User
        fields = ['id', 'email','is_tutor', 'is_active', 'password','full_name', 'phone_number',]
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

        user.set_password(password)
        user.save()
        return user


class UserLoginSerializer(ModelSerializer):
    email = EmailField(max_length=100, write_only=True)
    token = serializers.SerializerMethodField(read_only=True)
    profile = serializers.SerializerMethodField(read_only=True)
    is_tutor = serializers.BooleanField()

    class Meta:
        model = CustomUser
        fields = [
            'email',
            'token',
            'profile',
            'is_tutor',
            'password',
        ]
        extra_kwargs = {"password": {"write_only": True}}

    def get_profile(self, obj):
        email = obj.get('email')
        profile = Profile.objects.get(user__email=email)
        serializer = ProfileSerializer(profile, context=self.context)
        return serializer.data

    def get_token(self, obj):
        email = obj.get('email')
        token = Token.objects.get(user__email=email).key
        return token

    def save(self, **kwargs):
        data = self.validated_data
        email = data.get("email")
        password = data["password"]
        is_tutor = data["is_tutor"]
        if not email:
            raise ValidationError("Email cannot be blank")
        user = User.objects.filter(email=email)
        if user.exists():
            user_obj = User.objects.get(email=email)
            if user_obj.is_tutor == True and is_tutor == "True":
                pass   
            else:
                raise ValidationError("This email address is not registered as a tutor.")
        else:
            raise ValidationError("Invalid username or Password")

        if user_obj:
            if not user_obj.check_password(password):
                raise ValidationError("invalid password")
        token = Token.objects.get(user=user_obj).key
        user_data = {'token': token,
                        'user': {
                            'id': user_obj.pk,
                            'email': user_obj.email,
                            'is_tutor':user_obj.is_tutor,
                            'is_active': user_obj.is_active, 
                            'full_name': user_obj.full_name, 
                            'phone_number':user_obj.phone_number
                        }
                     }

        return user_data



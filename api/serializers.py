from rest_framework.serializers import ModelSerializer,CharField,EmailField,ModelSerializer,SerializerMethodField,ValidationError
from .models import UserLogin
from django.contrib.auth import get_user_model
from django.db import models

from django.db.models import Q


LoginUser = get_user_model()
class UserLoginSerializer(ModelSerializer):
    token = CharField(max_length = 100,allow_blank = True, read_only = True)
    email = EmailField(max_length = 100)

    class Meta:
        model = LoginUser  
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
        if not email:
            raise ValidationError("Email cannot be blank")

        user = UserLogin.objects.filter(Q(email = email)).distinct()
        user = user.exclude(email__isnull = True).exclude(email__iexact='')
        if user.exists() and user.count ==1:
            user_obj = user.First()
        else:
            raise ValidationError("Invalid userame or Password")

        if user_obj:
            if not user_obj.check_password(password):
                raise ValidationError("invalid password")




        return data

         

from rest_framework.serializers import ModelSerializer,CharField,EmailField,ModelSerializer,SerializerMethodField,ValidationError
from .models import UserLogin
from django.contrib.auth import get_user_model
from django.db import models

from django.db.models import Q


LoginUser = get_user_model()
class UserLoginSerializer(ModelSerializer):
    token = CharField(max_length = 100,allow_blank = True, read_only = True)
    username = CharField(max_length = 40)
    email = EmailField(max_length = 100)

    class Meta:
        model = LoginUser  
        fields = ['id', 
        'email',
        'password',
        'username',
        'token',
         ]

        extra_kwargs = {"password": {"write_only":True}}

    def validate(self, data):
        user_obj = None
        email = data.get("email")
        username = data.get("username")
        password = data.get("password")
        if not email and not username:
            raise ValidationError("Email   and username cannot be blank")

        user = UserLogin.objects.filter(Q(email = email)| Q(username = username)).distinct()
        user = user.exclude(email__isnull = True).exclude(email__iexact='')
        if user.exists() and user.count ==1:
            user_obj = user.First()
        else:
            raise ValidationError("Invalid userame or Password")

        if user_obj:
            if not user_obj.check_password(password):
                raise ValidationError("invalid password")
        data["token"] = "oiehroh5858reogrrkjgti"
        print(data["username"])




        return data

         

from django.conf import settings
from rest_framework import serializers
from rest_framework.validators import UniqueValidator
from django.contrib.auth import get_user_model

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
from rest_framework import serializers
from .models import Request
from django.contrib.auth import get_user_model


User = get_user_model()

class UserSerializer(serializers.ModelSerializer):

    class Meta:
        model=User
        exclude = ['password', 'last_login']

class CreateRequestSerializer(serializers.ModelSerializer):
    requester = serializers.SerializerMethodField(read_only=True)
    tutor = serializers.SerializerMethodField(read_only=True)
    requester_id = serializers.CharField(write_only=True)
    tutor_id = serializers.CharField(write_only=True)

    class Meta:
        model = Request
        fields = '__all__'

    def get_tutor(self, data):
        id = data.get('tutor_id')
        tutor_qs = User.objects.filter(pk=id, is_tutor=True)
        if not tutor_qs.exists():
            raise serializers.ValidationError('a tutor with that id does not exist')
        tutor = tutor_qs.first()
        serializer = UserSerializer(tutor.__dict__)
        tutor_f = serializer.data
        print(tutor_f, 'llll')
        return tutor_f

    def get_requester(self, data):
        id = data.get('requester_id')
        requester = User.objects.filter(pk=id)
        serializer = UserSerializer(requester.first().__dict__)
        return serializer.data

    def save(self):
        data = self.validated_data
        requester_id = data.get('requester_id')
        tutor_id = data.get('tutor_id')
        description = data.get('description')


        requester = User.objects.get(id=requester_id)
        tutor_qs = User.objects.filter(pk=tutor_id, is_tutor=True)

        if not tutor_qs.exists():
            raise serializers.ValidationError('a tutor with that id does not exist')

        request = Request.objects.create(
            requester=requester,
            tutor=tutor_qs.first(),
            description = description
        )
        return request

class RequestTutorSerializer(serializers.ModelSerializer):
    requester = UserSerializer(read_only=True)

    class Meta:
        model=Request
        exclude = ['tutor']

class RequestSerializer(serializers.ModelSerializer):
    tutor = UserSerializer(read_only=True)

    class Meta:
        model=Request
        exclude = ['requester']
from rest_framework import serializers
from api.models import Profile
from accounts.models import CustomUser
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

class CustomUserSerializer(serializers.HyperlinkedModelSerializer):
    profile_url = serializers.HyperlinkedIdentityField(
        view_name='profile-detail')
    class Meta:
        model = CustomUser
        depth = 1
        fields = ('id', 'full_name', 'email', 'phone_number',
                  'is_admin', 'is_active', 'is_tutor', 'profile', 'profile_url')



class ProfileSerializer(serializers.HyperlinkedModelSerializer):
    user_url = serializers.HyperlinkedIdentityField(view_name='customuser-detail')
    id = serializers.IntegerField(source='pk', read_only=True)
    email = serializers.CharField(source='user.email')
    full_name = serializers.CharField(source='user.full_name')

    class Meta:
        model = Profile
        depth = 1
        fields = ('id', 'email', 'full_name',
                  'profile_pic', 'desc', 'field', 'major_course', 'other_courses', 'state', 'address', 
                  'user_url')

    def get_full_name(self, obj):
        request = self.context['request']
        return request.user.get_full_name()

    def update(self, instance, validated_data):
        # retrieve CustomUser
        user_data = validated_data.pop('user', None)
        for attr, value in user_data.items():
            setattr(instance.user, attr, value)

        # retrieve Profile
        for attr, value in validated_data.items():
            setattr(instance, attr, value)
        instance.user.save()
        instance.save()
        return instance

class UserProfileSerializer(serializers.HyperlinkedModelSerializer):
    user_url = serializers.HyperlinkedIdentityField(view_name='customuser-detail')
    id = serializers.IntegerField(source='pk', read_only=True)
    email = serializers.CharField(source='user.email')
    full_name = serializers.CharField(source='user.full_name')

    class Meta:
        model = Profile
        depth = 1
        fields = ('id', 'email', 'full_name',
                  'profile_pic', 'desc', 'field', 'major_course', 'other_courses', 'state', 'address', 
                  'user_url')

    def get_full_name(self, obj):
        request = self.context['request']
        return request.user.get_full_name()

        # retrieve Profile
        for attr, value in validated_data.items():
            setattr(instance, attr, value)
        instance.user.save()
        instance.save()
        return instance
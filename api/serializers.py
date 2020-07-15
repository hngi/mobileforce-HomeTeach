from rest_framework import serializers
from .models import Profile, Rating
from accounts.models import CustomUser
from django.db.models import Avg, Count
from .models import Request
from django.contrib.auth import get_user_model

# Students should be able to filter list of Tutors based on field, gender, proximity 
User = get_user_model()

class UserSerializer(serializers.ModelSerializer):

    class Meta:
        model=User
        exclude = ['password', 'last_login']

class UpdateUserSerializer(serializers.ModelSerializer):
    email = serializers.CharField(read_only=True)
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


        requester = User.objects.get(id=requester_id)
        tutor_qs = User.objects.filter(pk=tutor_id, is_tutor=True)

        if not tutor_qs.exists():
            raise serializers.ValidationError('a tutor with that id does not exist')

        request = Request.objects.create(
            requester=requester,
            tutor=tutor_qs.first(),
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
    user = UserSerializer(read_only=True)
    user_url = serializers.HyperlinkedIdentityField(view_name='customuser-detail')
    rating = serializers.SerializerMethodField(read_only=True)

    class Meta:
        model = Profile
        depth = 1
        fields = ('user',
                  'profile_pic', 'hourly_rate', 'rating', 'desc', 'field', 'major_course', 'other_courses', 'state', 'address',
                  'user_url')

    def get_full_name(self, obj):
        request = self.context['request']
        return request.user.get_full_name()

    def get_rating(self, obj):
        user = obj.user
        rating = obj.rating.all().aggregate(rating=Avg('rate'), count=Count('user'))
        return rating

    def update(self, instance, validated_data):
        # retrieve CustomUser
        user_data = validated_data.pop('user', None)
        user_data = {k:v for k,v in user_data.items() if v}
        for attr, value in user_data.items():
            setattr(instance.user, attr, value)

        # retrieve Profile
        for attr, value in validated_data.items():
            setattr(instance, attr, value)
        instance.user.save()
        instance.save()
        return instance

class TutorProfileSerializer(serializers.HyperlinkedModelSerializer):
    user_url = serializers.HyperlinkedIdentityField(view_name='customuser-detail')
    user = UserSerializer(read_only=True)
    rating = serializers.SerializerMethodField(read_only=True)

    class Meta:
        model = Profile
        depth = 1
        fields = ('user','rating',
                  'profile_pic', 'desc','hourly_rate', 'field', 'major_course', 'other_courses', 'state', 'address',
                  'user_url')

    def get_full_name(self, obj):
        request = self.context['request']
        return request.user.get_full_name()


    def get_rating(self, obj):
        user = obj.user
        rating = obj.rating.all().aggregate(rating=Avg('rate'), count=Count('user'))
        return rating
    
class StudentProfileSerializer(serializers.HyperlinkedModelSerializer):
    user_url = serializers.HyperlinkedIdentityField(view_name='customuser-detail')
    user = UserSerializer(read_only=True)

    class Meta:
        model = Profile
        depth = 1
        fields = ('user'
                  'profile_pic', 'desc', 'field', 'major_course', 'other_courses', 'state', 'address', 
                  'user_url')

    def get_full_name(self, obj):
        request = self.context['request']
        return request.user.get_full_name()


class RatingsSerializer(serializers.ModelSerializer):
    user = UserSerializer(required=False)
    rate = serializers.IntegerField()
    student_id = serializers.CharField(write_only=True)
    tutor_id = serializers.CharField(write_only=True)

    class Meta:
        model = Rating
        fields = '__all__'
        extra_kwargs = {
            'rating': {'decimal_places': 1}
        }

    def save(self):
        data = self.validated_data
        rate = data.get('rate')
        student_id = data.get('student_id')
        tutor_id = data.get('tutor_id')

        tutor = User.objects.get(id=tutor_id)
        student = User.objects.get(id=student_id)

        if tutor.profile.rating.filter(user__email=student.email).exists():
            rating = tutor.profile.rating.get(user__email=student.email)
            tutor.profile.rating.remove(rating)
            rate = Rating.objects.create(user=student, rate=rate)
            tutor.profile.rating.add(rate)
            return tutor
        rate = Rating.objects.create(user=student, rate=rate)
        tutor.profile.rating.add(rate)
        # tutor.profile.rating.save()
        return tutor


class ProfileUpdateSerializer(serializers.ModelSerializer):
    user = UpdateUserSerializer()

    class Meta:
        model=Profile
        fields = '__all__'

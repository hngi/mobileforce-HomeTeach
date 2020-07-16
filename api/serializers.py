from rest_framework import serializers
from .models import Profile, Rating, StudentSchedule, Days
from accounts.models import CustomUser
from django.db.models import Avg, Count
from .models import Request
from datetime import datetime
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
    hourly_rate = serializers.SerializerMethodField(read_only=True)

    class Meta:
        model=Request
        exclude = ['tutor']

    def get_hourly_rate(self, obj):
        tutor_id = self.validated_data.get('id')
        profile = Profile.objects.get(user__id=tutor_id)
        hourly_rate = profile.hourly_rate
        return hourly_rate

class RequestSerializer(serializers.ModelSerializer):
    tutor = UserSerializer(read_only=True)
    hourly_rate = serializers.SerializerMethodField(read_only=True)
    class Meta:
        model=Request
        exclude = ['requester']

    def get_hourly_rate(self, obj):
        tutor_id = self.validated_data.get('id')
        profile = Profile.objects.get(user__id=tutor_id)
        hourly_rate = profile.hourly_rate
        return hourly_rate

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
        fields = ('user',
                  'profile_pic', 'desc', 'field', 'major_course', 'other_courses', 'state', 'address', 
                  'user_url')

    def get_full_name(self, obj):
        request = self.context['request']
        return request.user.get_full_name()


class RatingsSerializer(serializers.ModelSerializer):
    user = UserSerializer(required=False)
    tutor = UserSerializer(required=False)
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

        try:
            tutor = User.objects.get(id=tutor_id)
            student = User.objects.get(id=student_id)
        except CustomUser.DoesNotExist:
            raise serializers.ValidationError("a user with that id does not exist")

        if tutor.profile.rating.filter(user__email=student.email).exists():
            Rating.objects.get(user=student, tutor=tutor).delete()
            rate = Rating.objects.create(user=student,tutor=tutor, rate=rate)
            tutor.profile.rating.add(rate)
            return tutor
        rate = Rating.objects.create(user=student,tutor=tutor, rate=rate)
        tutor.profile.rating.add(rate)
        return tutor


class DaysSerializer(serializers.ModelSerializer):
    day = serializers.CharField()
    start = serializers.DateTimeField(format="%H:%M", input_formats=['%H:%M', 'iso-8601'])
    end = serializers.DateTimeField(format="%H:%M", input_formats=['%H:%M', 'iso-8601'])

    class Meta:
        model = Days
        exclude = ['id', ]

class StudentScheduleSerializer(serializers.ModelSerializer):
    user = UserSerializer(required=False)
    tutor = UserSerializer(required=False)
    tutor_id = serializers.CharField()
    student_id = serializers.CharField()
    duration_start = serializers.DateField(format="%d-%m-%Y", input_formats=['%d-%m-%Y', 'iso-8601'])
    duration_end = serializers.DateField(format="%d-%m-%Y", input_formats=['%d-%m-%Y', 'iso-8601'])
    days = serializers.CharField()
    # active_days = serializers.SerializerMethodField(read_only=True)

    class Meta:
        model=StudentSchedule
        fields = '__all__'


    def save(self):
        data = self.validated_data
        # print(data.__dict__, data)
        duration_start = data.get('duration_start')
        duration_end = data.get('duration_end')

        tutor_id = data.get('tutor_id')
        student_id = data.get('student_id')

        days = data.get('days')
        days = days.split(',')

        def chunker(seq, size):
            return (seq[pos:pos + size] for pos in range(0, len(seq), size))

        try:
            print(tutor_id, student_id)
            tutor = User.objects.get(id=tutor_id)
            student = User.objects.get(id=student_id)
        except CustomUser.DoesNotExist:
            raise serializers.ValidationError("a user with that id does not exist")

        # formatted_duration_start = datetime.strptime(duration_start, '%d-%m-%Y')
        # formatted_duration_end = datetime.strptime(duration_end, '%d-%m-%Y')

        schedule = StudentSchedule.objects.create(user=student, tutor=tutor,
                                duration_start=duration_start, duration_end=duration_end)
        list_of_days = []
        for day in chunker(days, 3):
            print(day, day[0].strip(), 'yooo')
            formatted_day = day[0].strip()
            print(formatted_day, 'yololo')
            formatted_start_date = datetime.strptime(day[1].strip(), "%H:%M")
            fomatted_end_date = datetime.strptime(day[2].strip(), "%H:%M")
            new_day = Days.objects.create(start=formatted_start_date, day=formatted_day, end=fomatted_end_date)
            schedule.days.add(new_day)
            new_day = DaysSerializer(new_day)
            list_of_days.append(new_day.data)
        data = {}
        # for attr, value in schedule.items():
        #     data[attr] = value
        data['tutor_full_name'] = schedule.tutor.full_name
        data['days'] = list_of_days
        data['student_full_name'] = schedule.user.full_name
        data['duration_start'] = schedule.duration_start
        data['duration_end'] = schedule.duration_end
        print(data)
        return data


class ProfileUpdateSerializer(serializers.ModelSerializer):
    user = UpdateUserSerializer()

    class Meta:
        model=Profile
        fields = '__all__'

from rest_framework import serializers
from .models import Profile, Rating, StudentSchedule, Days
from accounts.models import CustomUser
from django.db.models import Avg, Count
from .models import Request
from datetime import datetime
from django.contrib.auth import get_user_model
from .models import CreditCardInfo, BankInfo, Verification, UserWallet

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
    id = serializers.CharField(read_only=True)
    requester = serializers.SerializerMethodField(read_only=True)
    tutor = serializers.SerializerMethodField(read_only=True)
    student_id = serializers.CharField(write_only=True)
    tutor_id = serializers.CharField(write_only=True)
    from_hour = serializers.CharField()
    from_minute = serializers.CharField()
    to_hour = serializers.CharField()
    to_minute = serializers.CharField()
    dates = serializers.ListField()
    schedule = serializers.CharField(required=False)

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
        id = data.get('student_id')
        requester = User.objects.filter(pk=id)
        serializer = UserSerializer(requester.first().__dict__)
        return serializer.data

    def save(self):
        data = self.validated_data
        student_id = data.get('student_id')
        tutor_id = data.get('tutor_id')
        from_hour = data.get('from_hour')
        from_minute = data.get('from_minute')
        to_hour = data.get('to_hour')
        to_minute = data.get('to_minute')
        dates = data.get('dates')


        requester = User.objects.get(id=student_id)
        tutor_qs = User.objects.filter(pk=tutor_id, is_tutor=True)

        if not tutor_qs.exists():
            raise serializers.ValidationError('a tutor with that id does not exist')

        # days_list = []
        # for date in dates:
        #     day = DaysSerializer(date)
        #     days_list.append(day.data)
        student_schedule = StudentSchedule.objects.create(tutor=tutor_qs.first(),
                                       user=requester,
                                       from_hour=from_hour,
                                       to_hour=to_hour,
                                       from_minute=from_minute,
                                       to_minute=to_minute,
                                       )
        for date in dates:
            d = Days.objects.create(day=date)
            student_schedule.days.add(d)
        request = Request.objects.create(
            requester=requester,
            tutor=tutor_qs.first(),
            schedule = student_schedule
        )

        return request

class StudentScheduleSerializer(serializers.ModelSerializer):
    class Meta:
        model = StudentSchedule
        fields = '__all__'


class RequestTutorSerializer(serializers.ModelSerializer):
    requester = UserSerializer(read_only=True)


    class Meta:
        model=Request
        exclude = ['tutor', 'schedule']


class RequestSerializer(serializers.ModelSerializer):
    tutor = UserSerializer(read_only=True)
    class Meta:
        model=Request
        exclude = ['requester', 'schedule']

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
        fields = ('user','id',
                  'hourly_rate', 'rating', 'desc', 
                  'field', 'major_course', 'other_courses', 
                  'state', 'address', 'user_url')

    def get_full_name(self, obj):
        request = self.context['request']
        return request.user.get_full_name()

    def get_rating(self, obj):
        user = obj.user
        rating = obj.rating.all().aggregate(rating=Avg('rate'), count=Count('user'))
        return rating

    def update(self, instance, validated_data):
        # retrieve CustomUser
        user_data = {k:v for k,v in validated_data.items() if v}
        if user_data:
            for attr, value in user_data.items():
                setattr(instance.user, attr, value)
        validated_data.pop('user', None)
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
        fields = ('user','id','rating', 'profile_pic', 
                  'desc', 'credentials', 'video', 
                  'hourly_rate', 'field', 'major_course', 
                  'other_courses', 'state', 'address','user_url')

    def get_full_name(self, obj):
        request = self.context['request']
        return request.user.get_full_name()

    def get_rating(self, obj):
        user = obj.user
        rating = obj.rating.all().aggregate(rating=Avg('rate'), count=Count('user'))
        return rating


    def update(self, instance, validated_data):
        # retrieve CustomUser
        # user_data = validated_data.pop('user', None)
        user_data = {k:v for k,v in validated_data.items() if v}
        if user_data:
            for attr, value in user_data.items():
                setattr(instance.user, attr, value)
        validated_data.pop('user', None)
        # retrieve Profile
        for attr, value in validated_data.items():
            setattr(instance, attr, value)
        instance.user.save()
        instance.save()
        return instance
    
class StudentProfileSerializer(serializers.HyperlinkedModelSerializer):
    user_url = serializers.HyperlinkedIdentityField(view_name='customuser-detail')
    user = UserSerializer(read_only=True)

    class Meta:
        model = Profile
        depth = 1
        fields = ('user','id',
                  'desc', 'profile_pic', 'field', 'major_course', 'other_courses', 'state', 'address', 
                  'user_url')

        def update(self, instance, validated_data):
            # retrieve CustomUser
            # user_data = validated_data.pop('user', None)
            user_data = {k:v for k,v in validated_data.items() if v}
            if user_data:
                for attr, value in user_data.items():
                    setattr(instance.user, attr, value)
            validated_data.pop('user', None)
            # retrieve Profile
            for attr, value in validated_data.items():
                setattr(instance, attr, value)
            instance.user.save()
            instance.save()
            return instance

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


class ProfileUpdateSerializer(serializers.ModelSerializer):
    user = UpdateUserSerializer()

    class Meta:
        model=Profile
        fields = '__all__'

class TopTutorSerializer(serializers.ModelSerializer):
    #rating = RatingsSerializer()
    # rating = TutorProfileSerializer()
    rating = serializers.SerializerMethodField(read_only=True)
    class Meta:
        model = CustomUser
        fields = ['rating','full_name']

    def get_rating(self, obj):
        data = []
        for rating in Rating.objects.values_list('tutor').annotate(ordering=Avg('rate'), count=Count('user')).order_by('-ordering'):
            rating = list(rating)
            avg_rate = rating[1]
            count = rating[2]
            user_rating = {}
            rating = rating[0]
            user = User.objects.get(id=rating)
            user_rating['full_name'] = user.full_name
            user_rating['id'] = user.id
            user_rating['hourly_rate'] = user.profile.hourly_rate
            user_rating['description'] = user.profile.desc
            user_rating['profile_pic'] = user.profile.profile_pic if user.profile.profile_pic else None
            user_rating['field'] = user.profile.field
            user_rating['avg_rate'] = str(avg_rate)
            user_rating['count'] = str(count)
            data.append(user_rating)
            # user_rating['obj'] = obj
        return data


class BankInfoSerializer(serializers.ModelSerializer):
    class Meta:
        model = BankInfo
        fields = ('id', 'user', 'bank_name', 'account_name', 'account_number')


class CreditCardInfoSerializer(serializers.ModelSerializer):
    class Meta:
        model = CreditCardInfo
        fields = '__all__'

class VerificationSerializer(serializers.ModelSerializer):
    email = serializers.EmailField(required=False)
    amount = serializers.IntegerField(required=False)
    authorization_code = serializers.CharField(required=False)
    reference = serializers.CharField()
    class Meta:
        model = Verification
        fields = ('user', 'email', 'amount', 'reference', 'authorization_code',)

class UserWalletSerializer(serializers.ModelSerializer):
    class Meta:
        model = UserWallet
        fields = ('total_balance', 'available_balance',)
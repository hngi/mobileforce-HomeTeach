from rest_framework import serializers
from .models import Profile, Rating, StudentSchedule, Days
from accounts.models import CustomUser
from django.db.models import Avg, Count
from .models import Request
from datetime import datetime
from django.contrib.auth import get_user_model
from .models import CreditCardInfo, BankInfo, Verification, UserWallet, Favourites

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

class DaysSerializer(serializers.ModelSerializer):
    class Meta:
        model = Days
        fields = '__all__'
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
    subject = serializers.CharField()
    grade = serializers.CharField()
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
        subject = data.get('subject')
        grade = data.get('grade')
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
                                       subject=subject,
                                       grade=grade,
                                       )

        for date in dates:
            new_date = datetime.strptime(date, '%d-%m-%Y')
            # d = Days.objects.create(day=date)
            d = DaysSerializer(data={'day':date})
            if d.is_valid(raise_exception=True):
                day = d.save()
            student_schedule.days.add(day)
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

class ClassesRequestSerializer(serializers.Serializer):
    tutor_id = serializers.CharField()
    requests = serializers.SerializerMethodField()

    def get_requests(self, values):
        data = values
        dictionary = {k:v for k,v in data.items()}
        tutor_id = dictionary['tutor_id']
        try:
            tutor = User.objects.get(id=tutor_id, is_tutor=True)
        except User.DoesNotExist:
            raise serializers.ValidationError('A tutor with that id does not exist')
        schedule_details = tutor.tutor.all()
        requests = tutor.requests.all()
        requests_list = []
        for request in requests:
            print(request.schedule.days.all())
            for day in request.schedule.days.all():
                data = {    
                            'accepted':request.accepted,
                            'declined':request.declined,
                            'request_id':str(request.id),
                            'date_requested':datetime.strftime(request.date_requested, '%d-%m-%Y'),
                            'classes_request_id':f'{request.id}-{day.id}',
                            'subject':request.schedule.subject,
                            'day':datetime.strftime(day.day, '%d-%m-%Y'),
                            'from_minute':request.schedule.from_minute,
                            'to_minute':request.schedule.to_minute,
                            'from_hour':request.schedule.from_hour,
                            'to_hour':request.schedule.to_hour,
                            'grade':request.schedule.grade,
                            'student_name':request.schedule.user.full_name,
                            'student_id':str(request.schedule.user.id),
                            'student_pic':request.schedule.user.profile.profile_pic.url
                        }
                requests_list.append(data)
            requests_list.sort(key = lambda date: (date['date_requested'], datetime.strptime(date['day'], '%d-%m-%Y'), date['from_hour'], date['from_minute']))
        return requests_list


class StudentsClassesRequestSerializer(serializers.Serializer):
    student_id = serializers.CharField()
    requests = serializers.SerializerMethodField()

    def get_requests(self, values):
        data = values
        dictionary = {k:v for k,v in data.items()}
        student_id = dictionary['student_id']
        try:
            student = User.objects.get(id=student_id, is_tutor=False)
        except User.DoesNotExist:
            raise serializers.ValidationError('A student with that id does not exist')
        requests = student.pending_requests.all()
        requests_list = []
        for request in requests:
            print(request.schedule.days.all())
            for day in request.schedule.days.all():
                data = {    
                            'accepted':request.accepted,
                            'declined':request.declined,
                            'request_id':str(request.id),
                            'date_requested':datetime.strftime(request.date_requested, '%d-%m-%Y'),
                            'classes_request_id':f'{request.id}-{day.id}',
                            'subject':request.schedule.subject,
                            'day':datetime.strftime(day.day, '%d-%m-%Y'),
                            'from_minute':request.schedule.from_minute,
                            'to_minute':request.schedule.to_minute,
                            'from_hour':request.schedule.from_hour,
                            'to_hour':request.schedule.to_hour,
                            'grade':request.schedule.grade,
                            'tutor_name':request.schedule.tutor.full_name,
                            'tutor_id':str(request.schedule.tutor.id),
                            'tutor_pic':request.schedule.tutor.profile.profile_pic.url
                        }
                requests_list.append(data)
            requests_list.sort(key = lambda date: (date['date_requested'], datetime.strptime(date['day'], '%d-%m-%Y'), date['from_hour'], date['from_minute']))
        return requests_list


class ClassesSerializer(serializers.Serializer):
    tutor_id = serializers.CharField()
    schedules = serializers.SerializerMethodField()

    def get_schedules(self, values):
        data = values
        dictionary = {k:v for k,v in data.items()}
        tutor_id = dictionary['tutor_id']
        try:
            tutor = User.objects.get(id=tutor_id, is_tutor=True)
        except User.DoesNotExist:
            raise serializers.ValidationError('A tutor with that id does not exist')
        schedule_details = tutor.tutor.all()
        requests = tutor.requests.all()

        schedules = []
        for request in requests:
            if request.accepted == True:
                for day in request.schedule.days.all():
                    data = {   
                                'schedule_id':f'{request.schedule.id}-{day.id}',
                                'subject':request.schedule.subject,
                                'day':datetime.strftime(day.day, '%d-%m-%Y'),
                                'from_minute':request.schedule.from_minute,
                                'to_minute':request.schedule.to_minute,
                                'from_hour':request.schedule.from_hour,
                                'to_hour':request.schedule.to_hour,
                                'grade':request.schedule.grade,
                                'student_name':request.schedule.user.full_name,
                                'student_id':str(request.schedule.user.id),
                                'student_pic':request.schedule.user.profile.profile_pic.url
                            }
                    schedules.append(data)
        schedules.sort(key = lambda date: (datetime.strptime(date['day'], '%d-%m-%Y'), date['from_hour'], date['from_minute']))
        return schedules


class StudentsClassesSerializer(serializers.Serializer):
    student_id = serializers.CharField()
    requests = serializers.SerializerMethodField()

    def get_requests(self, values):
        data = values
        dictionary = {k:v for k,v in data.items()}
        student_id = dictionary['student_id']
        try:
            student = User.objects.get(id=student_id, is_tutor=False)
        except User.DoesNotExist:
            raise serializers.ValidationError('A student with that id does not exist')
        requests = student.pending_requests.all()
        requests_list = []
        for request in requests:
            if request.accepted == True:
                for day in request.schedule.days.all():
                    data = {    
                                'accepted':request.accepted,
                                'declined':request.declined,
                                'request_id':str(request.id),
                                'date_requested':datetime.strftime(request.date_requested, '%d-%m-%Y'),
                                'classes_request_id':f'{request.id}-{day.id}',
                                'subject':request.schedule.subject,
                                'day':datetime.strftime(day.day, '%d-%m-%Y'),
                                'from_minute':request.schedule.from_minute,
                                'to_minute':request.schedule.to_minute,
                                'from_hour':request.schedule.from_hour,
                                'to_hour':request.schedule.to_hour,
                                'grade':request.schedule.grade,
                                'tutor_name':request.schedule.tutor.full_name,
                                'tutor_id':str(request.schedule.tutor.id),
                                'tutor_pic':request.schedule.tutor.profile.profile_pic.url
                            }
                    requests_list.append(data)
                    print(day)
            requests_list.sort(key = lambda date: (date['date_requested'], datetime.strptime(date['day'], '%d-%m-%Y'), date['from_hour'], date['from_minute']))
        return requests_list

          

class FavouriteTutorsSerializer(serializers.ModelSerializer):
    tutor_id = serializers.CharField()
    student_id = serializers.CharField()
    student = serializers.CharField(required=False)
    tutor = serializers.CharField(required=False)
    action = serializers.CharField()
    class Meta:
        model = Favourites
        fields = '__all__'

    def save(self):
        data = self.validated_data
        student_id = data.get('student_id')
        tutor_id = data.get('tutor_id')
        action = data.get('action')
        print(tutor_id, student_id)
        try:
            tutor = User.objects.get(id=tutor_id, is_tutor=True)
        except User.DoesNotExist:
            raise serializers.ValidationError('A tutor with that id does not exist')

        try:
            student = User.objects.get(id=student_id)
        except User.DoesNotExist:
            raise serializers.ValidationError('A student with that id does not exist')
        try:
            student_favourites = Favourites.objects.get(student=student)
        except Favourites.DoesNotExist:
            student_favourites = Favourites.objects.create(student=student)
        if action == 'add':
            if student_favourites.tutor.filter(id=tutor_id).exists():
                raise serializers.ValidationError({'message':f'{tutor.full_name} has already been added into favourites', 'status':False})
            student_favourites.tutor.add(tutor)
        if action == 'remove':
            if student_favourites.tutor.filter(id=tutor_id).exists():
                student_favourites.tutor.remove(tutor)
            else:
                raise serializers.ValidationError(
            {'message':f'{tutor.full_name} isnt among your favourites', 'status':False})
            

        return {'tutor':tutor.full_name}
        

        

class RequestTutorSerializer(serializers.ModelSerializer):
    requester = UserSerializer(read_only=True)
    students = serializers.SerializerMethodField(read_only=True)

    class Meta:
        model=Request
        exclude = ['tutor', 'schedule', 'students']

    def get_students(self, data):
        students =  


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
    student = serializers.SerializerMethodField(read_only=True)

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
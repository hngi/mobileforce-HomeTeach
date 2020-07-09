from rest_framework import serializers
from api.models import Profile
from accounts.models import CustomUser

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
                  'desc', 'field', 'major_course', 'other_courses', 'state', 'address', 
                  'user_url')

    def get_full_name(self, obj):
        request = self.context['request']
        return request.user.get_full_name()

    def update(self, instance, validated_data):
        # retrieve the User
        user_data = validated_data.pop('user', None)
        for attr, value in user_data.items():
            setattr(instance.user, attr, value)

        # retrieve Profile
        for attr, value in validated_data.items():
            setattr(instance, attr, value)
        instance.user.save()
        instance.save()
        return instance
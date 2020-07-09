from rest_framework import serializers
from api.models import Profile
from accounts.models import CustomUser

class ProfileSerializer(serializers.ModelSerializer):
	class Meta:
		model = 'Profile'
		fields = ('desc', 'field', 'major_course', 'other_courses', 'state', 'address', 'rate',)


class CustomUserSerializer(serializers.ModelSerializer):
    profile = ProfileSerializer(required=True)
    class Meta:
        model = CustomUser
        fields = ('fullname', 'email', 'profile', 'created',)

    def create(self, validated_data):

        # create user 
        user = CustomUser.objects.create(
            fullname = validated_data['fullname'],
            email = validated_data['email'],
            # etc ...
        )

        profile_data = validated_data.pop('profile')
        # create profile
        profile = Profile.objects.create(
            user = user
            first_name = profile_data['first_name'],
            last_name = profile_data['last_name'],
            # etc...
        )

 
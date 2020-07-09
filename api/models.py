from django.db import models
from django.conf import settings
from django.db.models.signals import post_save
from django.dispatch import receiver
from accounts.models import CustomUser

# Create your models here.
class Profile(models.Model):
	user = models.OneToOneField(settings.AUTH_USER_MODEL,
							on_delete=models.CASCADE)
	desc = models.CharField(max_length=255)
	field = models.CharField(max_length=255)
	major_course = models.CharField(max_length=255)
	other_courses = models.CharField(max_length=255)
	state = models.CharField(max_length=255)
	address = models.CharField(max_length=255)
	

	def __unicode__(self):
		return f'Profile for user: {0}'.format(self.user.email)

	@receiver(post_save, sender=CustomUser)
	def create_user_profile(sender, instance, created, **kwargs):
		if created:
			Profile.objects.create(user=instance)

	@receiver(post_save, sender=CustomUser)
	def save_user_profile(sender, instance, **kwargs):
		instance.profile.save()

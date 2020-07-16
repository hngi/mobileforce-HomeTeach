from django.db import models
from django.conf import settings
from django.db.models.signals import post_save
from django.dispatch import receiver
from accounts.models import CustomUser
from django.contrib.auth import get_user_model

User = get_user_model()

FIELD_CHOICES = (
		('SCIENCE', 'Science'),
		('COMMERCIAL', 'Commercial'),
		('ARTS', 'Arts'),
		('ENGLISH', 'English'),
		('NON_ACADEMIC', 'Non-Academic'),
		('TEST_PREPARATION', 'Test Preparation'),
		('OTHER', 'Other'),
	)

STATE_CHOICES = (
		('ABUJA FCT', 'Abuja'),
		('ABIA', 'Abia'),
		('ADAMAWA', 'Adamawa'),
		('AKWA_IBOM', 'Akwa Ibom'),
		('ANAMBRA', 'Anambra'),
		('BAUCHI', 'Bauchi'),
		('BAYELSA', 'Bayelsa'),
		('BENUE', 'Benue'),
		('BORNO', 'Borno'),
		('CROSS_RIVER', 'Cross River'),
		('DELTA', 'Delta'),
		('EBONYI', 'Ebonyi'),
		('EDO', 'Edo'),
		('EKITI', 'Ekiti'),
		('ENUGU', 'Enugu'),
		('GOMBE', 'Gombe'),
		('IMO', 'Imo'),
		('JIGAWA', 'Jigawa'),
		('KADUNA', 'Kaduna'),
		('KANO', 'Kano'),
		('KATSINA', 'Kastina'),
		('KEBBI', 'Kebbi'),
		('KOGI', 'Kogi'),
		('KWARA', 'Kwara'),
		('LAGOS', 'Lagos'),
		('NASSARAWA', 'Nassarawa'),
		('NIGER', 'Niger'),
		('OGUN', 'Ogun'),
		('ONDO', 'Ondo'),
		('OSUN', 'Osun'),
		('OYO', 'Oyo'),
		('PLATEAU', 'Plateau'),
		('RIVERS', 'Rivers'),
		('SOKOTO', 'Sokoto'),
		('TARABA', 'Taraba'),
		('YOBE', 'Yobe'),
		('ZAMFARA', 'Zamfara'),
	)

class Rating(models.Model):
   tutor = models.ForeignKey(User, related_name='ratings_tutor', on_delete=models.CASCADE)
   user = models.ForeignKey(User, on_delete=models.CASCADE)
   rate = models.DecimalField(default=0.0, decimal_places=2, max_digits=5)


class Request(models.Model):
	requester = models.ForeignKey(User, related_name='pending_requests', on_delete=models.CASCADE)
	tutor = models.ForeignKey(User, related_name='requests', on_delete=models.CASCADE)
	date_requested = models.DateTimeField(auto_now_add=True)
	accepted = models.BooleanField(default=False)

	def __str__(self):
		return f'{self.requester.full_name} requests {self.tutor.full_name}'


# Create your models here.
class Profile(models.Model):
	user = models.OneToOneField(settings.AUTH_USER_MODEL,
								on_delete=models.CASCADE)
	# if user.is_tutor:
	profile_pic = models.ImageField(upload_to='images/%Y/%m/%d/',
									null=True, blank=True)
	credentials = models.FileField(upload_to='docs/%Y/%m/%d/',
								   null=True, blank=True)
	video = models.FileField(upload_to='videos/%Y/%m/%d/',
							 null=True, blank=True)
	rating = models.ManyToManyField(Rating, blank=True)
	desc = models.TextField(max_length=255, null=True, blank=True)
	field = models.CharField(max_length=255, choices = FIELD_CHOICES, blank=True)
	hourly_rate = models.CharField(max_length=10000000, default=0)
	major_course = models.CharField(max_length=255, null=True, blank=True)
	other_courses = models.CharField(max_length=255, null=True, blank=True)
	state = models.CharField(max_length=255, choices = STATE_CHOICES, blank=True)
	address = models.CharField(max_length=255, null=True, blank=True)	

	def __unicode__(self):
		return f'Profile for user: {self.user.email}'
		
	def no_of_ratings(self):
		ratings = Rating.objects.filter(user =self.user)
		return len(ratings)

	@receiver(post_save, sender=settings.AUTH_USER_MODEL)
	def create_user_profile(sender, instance, created, **kwargs):
		if created:
			Profile.objects.create(user=instance)

	@receiver(post_save, sender=settings.AUTH_USER_MODEL)
	def save_user_profile(sender, instance, **kwargs):
		instance.profile.save()


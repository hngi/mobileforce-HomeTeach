from django.db import models
from django.conf import settings
from django.db.models.signals import post_save
from django.dispatch import receiver
from accounts.models import CustomUser
from django.contrib.auth import get_user_model

User = get_user_model()


class Rating(models.Model):
	tutor = models.ForeignKey(User, related_name='ratings_tutor', on_delete=models.CASCADE)
	user = models.ForeignKey(User, on_delete=models.CASCADE)
	rate = models.DecimalField(default=0.0, decimal_places=2, max_digits=5)



class Request(models.Model):
	requester = models.ForeignKey(User, related_name='pending_requests', on_delete=models.CASCADE)
	tutor = models.ForeignKey(User, related_name='requests', on_delete=models.CASCADE)
	schedule = models.ForeignKey('StudentSchedule', on_delete=models.CASCADE)
	date_requested = models.DateTimeField(auto_now_add=True)
	declined = models.BooleanField(default=False)
	accepted = models.BooleanField(default=False)

	def __str__(self):
		return f'{self.requester.full_name} requests {self.tutor.full_name}'

class Days(models.Model):
	day = models.CharField(max_length=10)

class StudentSchedule(models.Model):
	user = models.ForeignKey(User,  on_delete=models.CASCADE)
	tutor = models.ForeignKey(User, related_name='tutor', on_delete=models.CASCADE)
	from_hour = models.CharField(max_length=10)
	from_minute = models.CharField(max_length=10)
	to_hour = models.CharField(max_length=10)
	to_minute = models.CharField(max_length=10)
	days = models.ManyToManyField(Days, blank=True, null=True)

# Create your models here.
class Profile(models.Model):
	user = models.OneToOneField(User,
								on_delete=models.CASCADE)
	# if user.is_tutor:
	profile_pic = models.ImageField(default='default/default.jpg', upload_to='images/%Y/%m/%d/',
									null=True, blank=True)
	credentials = models.FileField(upload_to='docs/%Y/%m/%d/',
								   null=True, blank=True)
	video = models.FileField(upload_to='videos/%Y/%m/%d/',
							 null=True, blank=True)
	rating = models.ManyToManyField(Rating, blank=True)
	desc = models.TextField(max_length=255, null=True, blank=True)
	field = models.CharField(max_length=255, blank=True)
	hourly_rate = models.CharField(max_length=10000000, default=0)
	major_course = models.CharField(max_length=255, null=True, blank=True)
	other_courses = models.CharField(max_length=255, null=True, blank=True)
	state = models.CharField(max_length=255, blank=True)
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

class BankInfo(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    bank_name = models.CharField(max_length=255, null=True, blank=True)
    account_name = models.CharField(max_length=255, null=True, blank=True)
    account_number = models.IntegerField(null=True, blank=True)
    routing_number = models.IntegerField(null=True, blank=True)
    social_security_number = models.IntegerField(null=True, blank=True)
	
    def __unicode__(self):
        return f'Bank Information for user: {self.user.full_name}'

class CreditCardInfo(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    card_holder_name = models.CharField(max_length=255, null=True)
    card_number = models.CharField(max_length=255, null=True)
    cvv = models.CharField(max_length=255, null=True)
    expiry_month = models.IntegerField(null=True)
    expiry_year = models.IntegerField(null=True)
	
    def __unicode__(self):
        return f'Bank Information for user: {self.user.full_name}'

class Verify(models.Model):
	user = models.ForeignKey(User, on_delete=models.CASCADE)
	amount = models.IntegerField()
	email = models.EmailField()
	reference = models.CharField(max_length=150)
	authorization_code = models.IntegerField()

class Wallet(models.Model):
	user = models.ForeignKey(User, on_delete=models.CASCADE)
	balance = models.IntegerField()

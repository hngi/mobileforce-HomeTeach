from django.db import models
from django.contrib.auth.models import (
	BaseUserManager, AbstractBaseUser
)
from django.dispatch import receiver
from django.db.models.signals import post_save
from django.core.validators import RegexValidator
from rest_framework.authtoken.models import Token
from django.conf import settings
from django.urls import reverse
from django_rest_passwordreset.signals import reset_password_token_created
from django.core.mail import send_mail  



class UserManager(BaseUserManager):
<<<<<<< HEAD
    def create_user(self, email, password=None):
        """
        Creates and saves a User with the given email, date of
        birth and password.
        """
        if not email:
            raise ValueError('Users must have an email address')

        user = self.model(
            email=UserManager.normalize_email(email),
        )

        user.set_password(password)
        user.save(using=self._db)
        return user

    def create_superuser(self, email, password):
        """
        Creates and saves a superuser with the given email, date of
        birth and password.
        """
        u = self.create_user(email,
                        password=password,

                    )
        u.is_admin = True
        u.is_active = True
        u.save(using=self._db)
        return u


class CustomUser(AbstractBaseUser):
    email = models.EmailField(
                        verbose_name='email address',
                        max_length=255,
                        unique=True,
                    )
    full_name = models.CharField(verbose_name='fullname', blank=True, max_length=150)
    phone_number = models.CharField(max_length=15, validators=[RegexValidator(r'^\d{1,15}$')])
    timestamp = models.DateTimeField(auto_now_add=True)
    is_tutor = models.BooleanField(default=False)
    is_admin = models.BooleanField(default=False)
    is_active = models.BooleanField(default=False)
=======
	def create_user(self, email, full_name, phone_number, password=None):
		"""
		Creates and saves a User with the given email and password.
		"""
		if not email:
			raise ValueError('Users must have an email address')

		user = self.model(
			email=UserManager.normalize_email(email),
			phone_number=phone_number,
			full_name=full_name
		)

		user.set_password(password)
		user.save(using=self._db)
		return user

	def create_superuser(self, email,password, full_name='', phone_number=''):
		"""
		Creates and saves a superuser with the given email, password.
		"""
		u = self.create_user(email=UserManager.normalize_email(email),
							password=password,
							 full_name=full_name,
							 phone_number=phone_number
						)
		u.is_admin = True
		u.save(using=self._db)
		return u


class CustomUser(AbstractBaseUser):
	email = models.EmailField(
						verbose_name='email address',
						max_length=255,
						unique=True,
					)
	full_name = models.CharField(verbose_name='fullname', blank=True, max_length=150)
	phone_number = models.CharField(max_length=15, validators=[RegexValidator(r'^\d{1,15}$')])
	timestamp = models.DateTimeField(auto_now_add=True)
	is_tutor = models.BooleanField(default=False)
	is_admin = models.BooleanField(default=False)
>>>>>>> 02fae4b96acbb2c68820cd157994c3d8aee61680

	objects = UserManager()

	USERNAME_FIELD = 'email'
	REQUIRED_FIELDS = []


	def __str__(self):
		return self.email

<<<<<<< HEAD
    def has_perm(self, perm, obj=None):
        "Does the user have a specific permission?"
        # Simplest possible answer: Yes, always
        return True

    def has_module_perms(self, app_label):
        "Does the user have permissions to view the app `app_label`?"
        # Simplest possible answer: Yes, always
        return True

    @property
    def is_staff(self):
        "Is the user a member of staff?"
        # Simplest possible answer: All admins are staff
        return self.is_admin
=======
	def has_perm(self, perm, obj=None):
		# Simplest possible answer: Yes, always
		return True

	def has_module_perms(self, app_label):
		# Simplest possible answer: Yes, always
		return True

	@property
	def is_staff(self):
		# Simplest possible answer: All admins are staff
		return self.is_admin

	@property
	def is_active(self):
		# Simplest possible answer: All admins are staff
		return self.is_admin
>>>>>>> 02fae4b96acbb2c68820cd157994c3d8aee61680


@receiver(post_save, sender=settings.AUTH_USER_MODEL)
def create_auth_token(sender, instance=None, created=False, **kwargs):
	if created:
		Token.objects.create(user=instance)


@receiver(reset_password_token_created)
def password_reset_token_created(sender, instance, reset_password_token, *args, **kwargs):

<<<<<<< HEAD
    email_content_message = "{}?token={}".format(reverse('password_reset:reset-password-request'), reset_password_token.key)

    send_mail(
        # title:
        "Password Reset for {title}".format(title="Home teach"),
        # message:
        email_content_message,
        # from:
        "noreply@somehost.local",
        # to:
        [reset_password_token.user.email]
    )
=======
	email_content_message = "{}?token={}".format(reverse('password_reset:reset-password-request'), reset_password_token.key)

	send_mail(
		# title:
		"Password Reset for {title}".format(title="Home teach"),
		# message:
		email_content_message,
		# from:
		"noreply@somehost.local",
		# to:
		[reset_password_token.user.email]
	)
>>>>>>> 02fae4b96acbb2c68820cd157994c3d8aee61680

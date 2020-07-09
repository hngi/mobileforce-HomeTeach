from django.db import models
from django.contrib.auth.models import (
    BaseUserManager, AbstractBaseUser
)
from django.dispatch import receiver
from django.db.models.signals import post_save
from django.core.validators import RegexValidator
from rest_framework.authtoken.models import Token
from django.conf import settings

class UserManager(BaseUserManager):
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

    objects = UserManager()

    USERNAME_FIELD = 'email'
    REQUIRED_FIELDS = []


    def __str__(self):
        return self.email

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


@receiver(post_save, sender=settings.AUTH_USER_MODEL)
def create_auth_token(sender, instance=None, created=False, **kwargs):
    if created:
        Token.objects.create(user=instance)
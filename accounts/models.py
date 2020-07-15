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
import uuid

from django_rest_passwordreset.tokens import get_token_generator
from django.contrib.auth import get_user_model


class UserManager(BaseUserManager):
    def create_user(self, email, password=None, full_name='', phone_number=''):
        """
        Creates and saves a User with the given email, date of
        birth and password.
        """
        if not email:
            raise ValueError('Users must have an email address')

        user = self.model(
            email=UserManager.normalize_email(email),
            full_name=full_name,
            phone_number=phone_number
        )

        user.set_password(password)
        user.save(using=self._db)
        return user

    def create_superuser(self, email, password, phone_number='', full_name=''):
        """
        Creates and saves a superuser with the given email, date of
        birth and password.
        """
        u = self.create_user(email,
                        password=password,
                        phone_number=phone_number,
                        full_name=full_name

                    )
        u.is_admin = True
        u.is_active = True
        u.save(using=self._db)
        return u

# uuid.uuid4().hex[:33].lower().strip('-')
class CustomUser(AbstractBaseUser):
    id = models.UUIDField(primary_key=True, default=uuid.uuid4, unique=True, editable=False)
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

    objects = UserManager()

    USERNAME_FIELD = 'email'
    REQUIRED_FIELDS = []


    def __str__(self):
        return self.email

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


@receiver(post_save, sender=settings.AUTH_USER_MODEL)
def create_auth_token(sender, instance=None, created=False, **kwargs):
    if created:
        Token.objects.create(user=instance)


@receiver(reset_password_token_created)
def password_reset_token_created(sender, instance, reset_password_token, *args, **kwargs):

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


AUTH_USER_MODEL = getattr(settings, 'AUTH_USER_MODEL', 'auth.CustomUser')

# get the token generator class
TOKEN_GENERATOR_CLASS = get_token_generator()

__all__ = [
    'ResetPasswordToken',
    'get_password_reset_token_expiry_time',
    'get_password_reset_lookup_field',
    'clear_expired',
]


class ResetPasswordToken(BaseUserManager):
    class Meta:

        verbose_name =  "Password Reset Token"
        verbose_name_plural =  "Password Reset Tokens"

    @staticmethod
    def generate_key():
        """ generates a pseudo random code using os.urandom and binascii.hexlify """
        return TOKEN_GENERATOR_CLASS.generate_token()

    id = models.AutoField(
        primary_key=True
    )

    user = models.ForeignKey(
        AUTH_USER_MODEL,
        related_name='password_reset_tokens',
        on_delete=models.CASCADE,
        verbose_name="The User which is associated to this password reset token"
    )

    created_at = models.DateTimeField(
        auto_now_add=True,
        verbose_name="When was this token generated"
    )

    # Key field, though it is not the primary key of the model
    key = models.CharField(
        "Key",
        max_length=64,
        db_index=True,
        unique=True
    )

    ip_address = models.GenericIPAddressField(
        "The IP address of this session",
        default="",
        blank=True,
        null=True,
    )
    user_agent = models.CharField(
        max_length=256,
        verbose_name="HTTP User Agent",
        default="",
        blank=True,
    )

    def save(self, *args, **kwargs):
        if not self.key:
            self.key = self.generate_key()
        return super(ResetPasswordToken, self).save(*args, **kwargs)

    def __str__(self):
        return "Password reset token for user {user}".format(user=self.user)


def get_password_reset_token_expiry_time():
    """
    Returns the password reset token expirty time in hours (default: 24)
    Set Django SETTINGS.DJANGO_REST_MULTITOKENAUTH_RESET_TOKEN_EXPIRY_TIME to overwrite this time
    :return: expiry time
    """
    # get token validation time
    return getattr(settings, 'DJANGO_REST_MULTITOKENAUTH_RESET_TOKEN_EXPIRY_TIME', 24)


def get_password_reset_lookup_field():
    """
    Returns the password reset lookup field (default: email)
    Set Django SETTINGS.DJANGO_REST_LOOKUP_FIELD to overwrite this time
    :return: lookup field
    """
    return getattr(settings, 'DJANGO_REST_LOOKUP_FIELD', 'email')


def clear_expired(expiry_time):
    """
    Remove all expired tokens
    :param expiry_time: Token expiration time
    """
    ResetPasswordToken.objects.filter(created_at__lte=expiry_time).delete()

def eligible_for_reset(self):
    if not self.is_active:
        # if the user is active we dont bother checking
        return False
 
    if getattr(settings, 'DJANGO_REST_MULTITOKENAUTH_REQUIRE_USABLE_PASSWORD', True):
        # if we require a usable password then return the result of has_usable_password()
        return self.has_usable_password()
    else:
        # otherwise return True because we dont care about the result of has_usable_password()
        return True

# add eligible_for_reset to the user class
UserModel = get_user_model()
UserModel.add_to_class("eligible_for_reset", eligible_for_reset)
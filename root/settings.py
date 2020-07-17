"""
Django settings for hometeach project.

Generated by 'django-admin startproject' using Django 3.0.8.

For more information on this file, see
https://docs.djangoproject.com/en/3.0/topics/settings/

For the full list of settings and their values, see
https://docs.djangoproject.com/en/3.0/ref/settings/
"""

import os
import django_heroku
import dj_database_url
from dotenv import load_dotenv

# Build paths inside the project like this: os.path.join(BASE_DIR, ...)
BASE_DIR = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))

load_dotenv(os.path.join(BASE_DIR, '.env'))

# Quick-start development settings - unsuitable for production
# See https://docs.djangoproject.com/en/3.0/howto/deployment/checklist/

# SECURITY WARNING: keep the secret key used in production secret!
SECRET_KEY = 'wi8ec5*+xx1z6h9&%h=iaauyl)7mbime7f7$l(hx1iwev0sb+='

# SECURITY WARNING: don't run with debug turned on in production!
DEBUG = True

ALLOWED_HOSTS = ['127.0.0.1', '0.0.0.0', 'localhost', 'api-hometeach.herokuapp.com']

# Application definition

INSTALLED_APPS = [
    'django.contrib.admin',
    'django.contrib.auth',
    'django.contrib.contenttypes',
    'django.contrib.sessions',
    'django.contrib.messages',
    'django.contrib.staticfiles',
    'django.contrib.sites',

    # local apps
    'accounts',
    'api',
    'confirmation',

    # third-party
    'rest_framework',
    'rest_framework.authtoken',
    'drf_yasg',
    'django_filters',
    'whitenoise.runserver_nostatic',
    'storages',
    'rest_auth',
]

SITE_ID = 1

AUTH_USER_MODEL = 'accounts.CustomUser'

MIDDLEWARE = [
    'django.middleware.security.SecurityMiddleware',
    'whitenoise.middleware.WhiteNoiseMiddleware',
    'django.contrib.sessions.middleware.SessionMiddleware',
    'django.middleware.common.CommonMiddleware',
    'django.middleware.csrf.CsrfViewMiddleware',
    'django.contrib.auth.middleware.AuthenticationMiddleware',
    'django.contrib.messages.middleware.MessageMiddleware',
    'django.middleware.clickjacking.XFrameOptionsMiddleware',
]


ROOT_URLCONF = 'root.urls'

TEMPLATES = [
    {
        'BACKEND': 'django.template.backends.django.DjangoTemplates',
        'DIRS': [],
        'APP_DIRS': True,
        'OPTIONS': {
            'context_processors': [
                'django.template.context_processors.debug',
                'django.template.context_processors.request',
                'django.contrib.auth.context_processors.auth',
                'django.contrib.messages.context_processors.messages',
                'django.template.context_processors.media',
            ],
        },
    },
]

WSGI_APPLICATION = 'root.wsgi.application'


# Database
# https://docs.djangoproject.com/en/3.0/ref/settings/#databases

DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.sqlite3',
        'NAME': os.path.join(BASE_DIR, 'db.sqlite3'),
    }
}

#db_from_env = dj_database_url.config(conn_max_age=600)
#DATABASES = { 'default': dj_database_url.config() }

prod_db  =  dj_database_url.config(conn_max_age=500)
DATABASES['default'].update(prod_db)

#db_from_env = dj_database_url.config(conn_max_age=600)
#DATABASES['default'].update(db_from_env)
# Password validation
# https://docs.djangoproject.com/en/3.0/ref/settings/#auth-password-validators

AUTH_PASSWORD_VALIDATORS = [
    {
        'NAME': 'django.contrib.auth.password_validation.UserAttributeSimilarityValidator',
    },
    {
        'NAME': 'django.contrib.auth.password_validation.MinimumLengthValidator',
    },
    {
        'NAME': 'django.contrib.auth.password_validation.CommonPasswordValidator',
    },
    {
        'NAME': 'django.contrib.auth.password_validation.NumericPasswordValidator',
    },
]


# Internationalization
# https://docs.djangoproject.com/en/3.0/topics/i18n/

LANGUAGE_CODE = 'en-us'

TIME_ZONE = 'UTC'

USE_I18N = True

USE_L10N = True

USE_TZ = True


DEFAULT_AUTHENTICATION_CLASSES = [
        'rest_framework.authentication.TokenAuthentication',
     ]

DEFAULT_PERMISSION_CLASSES = [
         'rest_framework.permissions.IsAuthenticated',
     ]

REST_FRAMEWORK = {
    'DEFAULT_AUTHENTICATION_CLASSES': DEFAULT_AUTHENTICATION_CLASSES,
    'DEFAULT_PERMISSION_CLASSES': DEFAULT_PERMISSION_CLASSES,
    'DEFAULT_FILTER_BACKENDS': (
        'django_filters.rest_framework.DjangoFilterBackend',
        'rest_framework.filters.SearchFilter',
    )
}

django_heroku.settings(locals())

SENDGRID_API_KEY = os.getenv('SENDGRID_API_KEY')

EMAIL_HOST = 'smtp.sendgrid.net'
EMAIL_PORT = 587
EMAIL_USE_TLS = True
EMAIL_HOST_USER = 'apikey'
EMAIL_HOST_PASSWORD = os.environ.get('SENDGRID_API_KEY')

# AWS settings for static and media files storage
AWS_ACCESS_KEY_ID = os.getenv("AWS_ACCESS_KEY_ID")
AWS_SECRET_ACCESS_KEY = os.getenv("AWS_SECRET_ACCESS_KEY")
AWS_STORAGE_BUCKET_NAME = os.getenv("AWS_STORAGE_BUCKET_NAME")
AWS_S3_CUSTOM_DOMAIN = '%s.s3.amazonaws.com' % AWS_STORAGE_BUCKET_NAME
AWS_S3_OBJECT_PARAMETERS = {
    'CacheControl': 'max-age=86400',
}
AWS_DEFAULT_ACL = None

# Static files (CSS, JavaScript, Images) settings
STATIC_LOCATION = 'static'
STATIC_URL = 'https://%s/%s/' % (AWS_S3_CUSTOM_DOMAIN, STATIC_LOCATION)
STATICFILES_STORAGE = 'root.storage_backends.StaticStorage'
STATICFILES_DIRS = [
    os.path.join(BASE_DIR,'static'),
]
# Media files settings
PUBLIC_MEDIA_LOCATION = 'media'
MEDIA_URL =  'https://%s/%s/' % (AWS_S3_CUSTOM_DOMAIN, PUBLIC_MEDIA_LOCATION)
DEFAULT_FILE_STORAGE = 'root.storage_backends.PublicMediaStorage'

# Paystack
PAYSTACK_AUTHORIZATION_KEY = os.environ.get('PAYSTACK_SECRET_KEY')

ENCRYPT_KEY = b'5R_y8WWIMF7MOhShxQiZFZwXcRGGKKbdGrkPN9iVVpc='

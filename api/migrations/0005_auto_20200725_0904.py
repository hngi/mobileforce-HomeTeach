# Generated by Django 3.0.8 on 2020-07-25 09:04

import datetime
from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('api', '0004_auto_20200723_1229'),
    ]

    operations = [
        migrations.AlterField(
            model_name='days',
            name='day',
            field=models.DateField(default=datetime.datetime(2020, 7, 25, 9, 4, 42, 12813)),
        ),
        migrations.AlterField(
            model_name='profile',
            name='hourly_rate',
            field=models.CharField(blank=True, default=0, max_length=10000000, null=True),
        ),
    ]

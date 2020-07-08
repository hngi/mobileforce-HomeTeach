from django.db import models


class UserLogin(models.Model):
    token = models.CharField(max_length = 100)
    email = models.EmailField(max_length = 100)
    username = models.CharField(max_length = 100)
    password = models.CharField(max_length = 50)

    def __str__(self):

        return self.username
    



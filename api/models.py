from django.db import models
from django.contrib.auth import get_user_model

User = get_user_model()

class Request(models.Model):
    requester = models.ForeignKey(User, related_name='pending_requests', on_delete=models.CASCADE)
    tutor = models.ForeignKey(User, related_name='requests', on_delete=models.CASCADE)
    description = models.CharField(max_length=1000)
    date_requested = models.DateTimeField(auto_now_add=True)
    accepted = models.BooleanField(default=False)

    def __str__(self):
        return f'{self.requester.full_name} requests {self.tutor.full_name}'

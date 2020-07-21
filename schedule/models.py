from django.db import models

class Schedule(models.Model):
    course_name = models.CharField(max_length=64)
    #tutor_name = models.ForeignKey(User, related_name='course_tutor', on_delete=models.CASCADE)
    tutor_name = models.CharField(max_length=64)
    class_date = models.DateField(blank=True, null=True)
    start_time = models.TimeField()
    end_time = models.TimeField()

def __str__(self):
    return self.course_name
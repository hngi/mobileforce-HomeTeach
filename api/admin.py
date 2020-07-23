from django.contrib import admin
from .models import Request, Profile, Rating, StudentSchedule, Days, Favourites

admin.site.register(Request)
admin.site.register(Profile)
admin.site.register(Rating)
admin.site.register(StudentSchedule)
admin.site.register(Days)
admin.site.register(Favourites)


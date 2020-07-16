from rest_framework import permissions
from rest_framework.views import APIView
from .models import Contact
from django.core.mail import send_mail
from rest_framework.response import Response

class ContactCreateView(APIView):
    permission_classes = (permissions.AllowAny, )

    def post(self, request, format=None):
        data = self.request.data
       # try:
        send_mail(
                data['subject'],
                'Course: ' 
                + data['courseName']
                + '\nTutor: '
                + data['tutorName']
                + '\n\nMessage:\n'
                + data['message'],
                'akinsolaademolatemitope@gmail.com',
                [data['email'],],
                # data['subject'],
                # data['name'],
                # data['message'],
                # [data['email'],],
                fail_silently=False
            )

        contact = Contact(courseName = data['courseName'], tutorName = data['tutorName'], email=data['email'], subject=data['subject'], message=data['message'])
        contact.save()

        return Response({'success': 'Message Sent successfully'})
        # except:
        #     return Response({'error' : 'Message failed to send'})


        # try:
        #     send_mail(
        #         data['subject'],
        #         'Name: ' 
        #         + data['name']
        #         + '\nEmail: '
        #         + data['email']
        #         + '\n\nMessage:\n'
        #         + data['message'],
        #         'mdbilal8022@gmail.com',
        #         '[mdbilal8022@gmail.com]',
        #         fail_silently=False
        #      )

        #     contact = Contact(name = data['name'], email=data['email'], subject=data['subject'], message=data['message'])
        #     contact.save()

        #     return Response({'success': 'Message Sent successfully'})
        # except:
        #     return Response({'error' : 'Message failed to send'})


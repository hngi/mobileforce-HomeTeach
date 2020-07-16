from rest_framework import serializers

class TransactionsSerializer(serializers.Serializer):
	email = serializers.EmailField(allow_blank=False)
	amount = serializers.CharField(allow_blank=False)
	ref = serializers.UUIDField(format='hex')


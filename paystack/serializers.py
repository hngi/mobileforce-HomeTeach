from rest_framework import serializers

class TransactionsSerializer(serializers.Serializer):
	email = serializers.EmailField(allow_blank=False)
	amount = serializers.IntegerField(max_value=None, min_value=None)
	#ref = serializers.UUIDField(format='hex')


from rest_framework.decorators import api_view
from rest_framework.response import Response
from .serializers import TaskSerializer
from django.contrib.auth.models import User
import json

@api_view(['POST'])
def create_task_view(request):
    return Response({'hello': 'world'})

@api_view(['POST'])
def get_tasks_view(request):
    # get param
    body_unicode = request.body.decode('utf-8')
    request_params = json.loads(body_unicode)
    username = request_params['username']
    # get user
    user = User.objects.get(username=username)
    # get tasks based on user
    tasks = user.task_set.all()
    serializer = TaskSerializer(tasks, many=True)
    return Response(serializer.data)

@api_view(['POST'])
def delete_task_view(request):
    return Response({'hello': 'world'})

@api_view(['POST'])
def update_task_view(request):
    return Response({'hello': 'world'})
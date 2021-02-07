from rest_framework.decorators import api_view
from rest_framework.response import Response
from .serializers import TaskSerializer
from django.contrib.auth.models import User
import json
from rest_framework import status
from .models import Task

@api_view(['POST'])
def create_task_view(request):
    # get param
    body_unicode = request.body.decode('utf-8')
    request_params = json.loads(body_unicode)
    username = request_params['username']
    serializer = TaskSerializer(data=request.data)
    if serializer.is_valid():
        user = User.objects.get(username=username)
        task = serializer.save(user=user)
        Response(serializer.data, status=200)
    return Response({}, status=400)

@api_view(['POST'])
def get_tasks_view(request):
    # get param
    body_unicode = request.body.decode('utf-8')
    request_params = json.loads(body_unicode)
    username = request_params['username']
    # get user
    user = User.objects.get(username=username)
    # get tasks based on user
    tasks = user.tasks.all()
    serializer = TaskSerializer(tasks, many=True)
    return Response(serializer.data)

@api_view(['POST'])
def delete_task_view(request):
    return Response({'hello': 'world'})

@api_view(['POST'])
def update_task_view(request):
    # get param
    body_unicode = request.body.decode('utf-8')
    request_params = json.loads(body_unicode)
    task_id = request_params['task_id']
    new_title = request_params['new_title']
    # get task
    task = Task.objects.get(id=task_id)
    task.title = new_title
    task.save()
    return Response({'messages': 'update success'}, status=200)
from rest_framework.decorators import api_view
from res_framework.response import Response

@api_view(['POST'])
def create_task_view(request):
    return Response({'hello': 'world'})

@api_view(['POST'])
def get_tasks_view(request):
    return Response({'hello': 'world'})

@api_view(['POST'])
def delete_task_view(request):
    return Response({'hello': 'world'})

@api_view(['POST'])
def update_task_view(request):
    return Response({'hello': 'world'})
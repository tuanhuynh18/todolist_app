from django.db import models
from django.contrib.auth.models import User

class Task(models.Model):
    title = models.CharField(max_length=200)
    complete = models.BooleanField(default=False, blank=True, null=True)
    user = models.ForeignKey(User, on_delete=models.CASCADE)

def __str__(self):
    return self.title
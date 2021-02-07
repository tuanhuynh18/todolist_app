from django.contrib import admin

from .models import Task


class TaskAdmin(admin.ModelAdmin):
    fields = ('title', 'user')
    list_display = ('title', 'user')

admin.site.register(Task, TaskAdmin)
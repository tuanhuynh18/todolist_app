# todolist_app

## Set up back-end:
install pipenv here https://pypi.org/project/pipenv/
run this command after install pipenv (make sure you at the back-end folder in terminal):
    $ pipenv install
    $ pipenv shell
    $ python manage.py makemigrations
    $ python manage.py makemigrations todo_list
    $ python manage.py migrate
    $ python manage.py createsuperuser
    (follow to step as you will be prompted create an admin account)
    $ python manage.py runserver

## API Endpoints: 
### status code
200 = success
400 = error

### localhost/dj-rest-auth/login/ (POST)
Request body
{
    "username": "username_here"
    "password": "password_here"
}
    
Return: 
{Tokenkey}

### localhost/dj-rest-auth/logout/ (POST)

### localhost/dj-rest-auth/registration/ (POST)
Request body
{
    "username": "username_here"
    "password1": "password1_here"
    "password2": "password2_here"
    "email": "email_here"
}

### localhost/get-tasks/ (POST)
Request body
{
    "username": "username_here"
}

Return:
{[
    {
        "id": 1,
        "title": "title here",
        "complete": false
        "username": "username_here"
    }
    ...
]}

### localhost/create-task/ (POST)
Request body
{
    "title": "title here",
    "complete": false,
    "username": "admin"
}
Return:
{}

### localhost/update-task/ (POST)
Request body
{
    "task_id": 1,
    "new_title": "new title here"
}

Return:
{
    "messages": "update success"
}

### localhost/delete-task/ (POST)
Request body
{
    "task_id": 1
}
Return:
{
    "messages": "delete success"
}
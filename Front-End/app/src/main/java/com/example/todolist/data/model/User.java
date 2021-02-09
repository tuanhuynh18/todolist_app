package com.example.todolist.data.model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class User {

    public static User getUser(JSONObject jsonObject, String username) throws JSONException {
        String token = jsonObject.getString("key");
        User user = new User(token, username);

        return user;
    }

    private static String token;
    private static String username;
    private static Task[] taskList;

    public User(String token, String username, Task[] taskList) {
        this.token = token;
        this.username = username;
        this.taskList = taskList;
    }

    public User(String token, String username) {
        this.token = token;
        this.username = username;
        this.taskList = new Task[20];
    }

    public User() {
        this.token = "";
        this.username = "Guest_User";
        this.taskList = new Task[20];
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public Task[] getTaskList() {
        return taskList;
    }

    public static void setUsername(String username) {
        User.username = username;
    }

    public void setTaskList(Task[] taskList) {
        User.taskList = new Task[taskList.length];
        for (int i = 0; i < taskList.length; i++) {
            int id = taskList[i].getId();
            String title = taskList[i].getTitle();
            Boolean complete = taskList[i].isComplete();
            Task task = new Task(id, title, complete);
            User.taskList[i] = task;
        }
    }
}

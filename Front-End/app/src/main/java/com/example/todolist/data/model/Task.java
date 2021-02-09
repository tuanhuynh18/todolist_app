package com.example.todolist.data.model;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;


public class Task {

    // { "id": 1, "title": "title here", "complete": false "username": "username_here" }

    private int id;
    private String title;
    private Boolean complete;

    public Task(int id, String title, Boolean complete) {
        this.id = id;
        this.title = title;
        this.complete = complete;
    }

    public static Task[] getTaskList(JSONArray jsonArray, String username) throws JSONException {

        Task[] taskList = new Task[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            int id = jsonArray.getJSONObject(i).getInt("id");
            String title = jsonArray.getJSONObject(i).getString("title");
            Boolean complete = jsonArray.getJSONObject(i).getBoolean("complete");
            Task task = new Task(id, title, complete);
            taskList[i] = task;
        }

        return taskList;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Boolean isComplete() {
        return complete;
    }
}

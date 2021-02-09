package com.example.todolist.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.todolist.R;
import com.example.todolist.data.model.AbstractAPIListener;
import com.example.todolist.data.model.Model;
import com.example.todolist.data.model.Task;
import com.example.todolist.data.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> items;
    private ArrayAdapter<String> itemAdapter;
    private ListView listView;
    private Button button;
    public static final String BASE_URL = "http://10.0.2.2:8000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Application application = this.getApplication();
        Model model = Model.getInstance(application);
        User user = model.getUser();

        Toast.makeText(application, "Login Successful! Token: " + model.getUser().getToken(), Toast.LENGTH_LONG).show();

        listView = findViewById(R.id.listView);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(view, user);
            }
        });

        items = new ArrayList<>();
        itemAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemAdapter);
        setUpListViewListener(user);

        // API call to populate list view with tasks
        String url = BASE_URL + "api/get-tasks/";
        JSONArray jsonArray = new JSONArray();
        String username = model.getUser().getUsername();
        RequestQueue requestQueue = Volley.newRequestQueue(application);

        if (!username.equals("Guest_User")) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("username", username);
                jsonArray.put(jsonObject);


                Response.Listener<JSONArray> successListener = new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            Task[] taskList = Task.getTaskList(response, username);
                            user.setTaskList(taskList);
                            if (taskList.length > 0) {
                                for (int i = 0; i < taskList.length; i++) {
                                    itemAdapter.add(taskList[i].getTitle());
                                }
                            } else {
                                Toast.makeText(application, "No tasks found. Add some tasks!", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(application, "JSON Exception: " + e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(application, "Unsuccessful response: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                };

                JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url, jsonArray, successListener, errorListener);
                requestQueue.add(request);
            } catch (JSONException e) {
                Toast.makeText(application, "JSON Exception", Toast.LENGTH_LONG).show();
            }
        }

    }

    private void setUpListViewListener(User user) {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getApplicationContext();

                String url = BASE_URL + "api/delete-task/";
                JSONObject jsonObject = new JSONObject();
                String username = user.getUsername();

                if (username.equals("Guest_User")) {
                    items.remove(position);
                    itemAdapter.notifyDataSetChanged();
                    Toast.makeText(context, "Item Removed", Toast.LENGTH_LONG).show();
                } else {
                    // Find id of task to be deleted
                    String task_selected = items.get(position);
                    Task[] taskList = user.getTaskList();
                    int task_id = -1;
                    for (int i = 0; i < taskList.length; i++) {
                        if (taskList[i].getTitle().equals(task_selected)) task_id = taskList[i].getId();
                    }

                    if (task_id != -1) {
                        try {
                            jsonObject.put("task_id", task_id);

                            Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    items.remove(position);
                                    itemAdapter.notifyDataSetChanged();
                                    Toast.makeText(context, "Item Removed", Toast.LENGTH_LONG).show();
                                }
                            };

                            Response.ErrorListener errorListener = new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(context, "Unsuccessful response: " + error.toString(), Toast.LENGTH_LONG).show();
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(context);
                            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, successListener, errorListener);
                            requestQueue.add(request);
                        } catch (JSONException e) {
                            Toast.makeText(context, "JSON Exception", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                return true;
            }
        });
    }

    private void addItem(View view, User user) {
        // "title": "title here", "complete": false, "username": "admin"
        EditText input = findViewById(R.id.editText);
        //input.requestFocus();
        String itemText = input.getText().toString();

        Context context = getApplicationContext();

        String url = BASE_URL + "api/create-task/";
        JSONObject jsonObject = new JSONObject();
        String username = user.getUsername();

        if (username.equals("Guest_User")) {
            if (!(itemText.equals(""))) {
                itemAdapter.add(itemText);
                input.setText("");
            } else {
                Toast.makeText(getApplicationContext(), "Input cannot be empty!", Toast.LENGTH_LONG).show();
            }
        } else {
            try {

                itemAdapter.add(itemText);
                input.setText("");

                jsonObject.put("title", itemText);
                jsonObject.put("complete", "false");
                jsonObject.put("username", username);

                Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Successful response: " + response.toString(), Toast.LENGTH_LONG).show();
                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Unsuccessful response: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, successListener, errorListener);
                requestQueue.add(request);
            } catch (JSONException e) {
                Toast.makeText(context, "JSON Exception", Toast.LENGTH_LONG).show();
            }
        }


    }
}
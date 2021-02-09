package com.example.todolist.data.model;

import android.app.Application;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WebAPI {
    public static final String BASE_URL = "http://10.0.2.2:8000/";

    private final Application mApplication;
    private RequestQueue mRequestQueue;

    public WebAPI(Application application) {
        mApplication = application;
        mRequestQueue = Volley.newRequestQueue(application);
    }

    public void login(String username, String password, final APIListener listener) {
        String url = BASE_URL + "dj-rest-auth/login/";
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);

            //Toast.makeText(mApplication, "Username: " + username + " Password: " + password, Toast.LENGTH_LONG).show();

            Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        User user = User.getUser(response, username);
                        //Toast.makeText(mApplication, "Login Successful: User " + username + " has logged in! Tokin: " + user.getToken(), Toast.LENGTH_LONG).show();
                        listener.onLogin(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(mApplication, "JSON Exception: " + e.toString(), Toast.LENGTH_LONG).show();
                    }

                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mApplication, "Unsuccessful response: " + error.toString(), Toast.LENGTH_LONG).show();
                }
            };

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, successListener, errorListener);
            mRequestQueue.add(request);
        }
        catch (JSONException e) {
            Toast.makeText(mApplication, "JSON Exception", Toast.LENGTH_LONG).show();
        }

    }
}

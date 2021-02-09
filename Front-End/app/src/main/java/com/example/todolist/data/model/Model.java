package com.example.todolist.data.model;

import android.app.Application;
import com.example.todolist.data.model.WebAPI;

public class Model {

    private static Model sIntance = null;
    private static WebAPI mAPI = null;
    private static User mUser;

    public static Model getInstance(Application application) {
        if (sIntance == null) {
            sIntance = new Model(application);
        }
        return sIntance;
    }

    private final Application mApplication;

    private Model(Application application) {
        mApplication = application;
        mAPI = new WebAPI(mApplication);
    }

    public Application getApplication() {return mApplication; }

    public void login(String username, String password, APIListener listener) {
        mAPI.login(username, password, listener);
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        this.mUser = user;
    }
}

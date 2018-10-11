package es.source.code.utils;

import android.app.Application;

import es.source.code.model.User;

public class MyApplication extends Application {

    private static MyApplication app;
    private User user;


    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        user = new User();
    }

    public static MyApplication getApp() {
        return app;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}

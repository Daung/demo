package com.wzy.livedatademo;

import android.app.Application;

public class MyApplication extends Application {
    private static MyApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        if (application == null) {
            application = this;
        }
     }

     public static MyApplication of() {
        return application;
     }
}

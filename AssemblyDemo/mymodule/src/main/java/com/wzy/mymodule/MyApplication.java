package com.wzy.mymodule;

import android.app.Application;

import com.wzy.basemodule.BaseApplication;
import com.wzy.basemodule.ServiceFactory;

public class MyApplication extends BaseApplication {
    private Application app;

    @Override
    public void initialize(Application app) {
        this.app = app;
        ServiceFactory.getInstance().setMyService(new MyService());
    }
}

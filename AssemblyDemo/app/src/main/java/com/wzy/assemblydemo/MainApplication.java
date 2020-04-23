package com.wzy.assemblydemo;

import android.app.Application;

import com.wzy.basemodule.AppConfig;
import com.wzy.basemodule.BaseApplication;

public class MainApplication extends Application {
    //组件化的一个坑，比如类名和布局名称不能和主工程起名一样
    //如果一样，就会以主工程的类名为主。
    @Override
    public void onCreate() {
        super.onCreate();
        for (String config : AppConfig.configs) {
            try {
                Class<?> aClass = Class.forName(config);
                Object object = aClass.newInstance();
                BaseApplication application = (BaseApplication) object;
                application.initialize(this);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}

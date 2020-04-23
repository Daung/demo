package com.wzy.loginmodule;

import android.app.Application;

import com.wzy.basemodule.BaseApplication;
import com.wzy.basemodule.ServiceFactory;

public class LoginApplication extends BaseApplication {
    private Application app;
    @Override
    public void initialize(Application app) {
        this.app = app;
        ServiceFactory.getInstance().setLoginService(new LoginService());
    }
}

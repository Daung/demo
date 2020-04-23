package com.wzy.loginmodule;

import android.content.Context;
import android.content.Intent;

import com.wzy.basemodule.ILoginService;

public class LoginService implements ILoginService {

    @Override
    public void goLoginActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }


}

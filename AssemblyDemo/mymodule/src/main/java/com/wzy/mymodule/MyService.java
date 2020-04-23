package com.wzy.mymodule;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.wzy.basemodule.IMyService;

public class MyService implements IMyService {

    @Override
    public void goMyActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MyActivity.class);
        context.startActivity(intent);
    }

    @Override
    public Fragment getLoginFragment(FragmentManager manager, int layoutId) {
        Fragment fragment = LoginFragment.getInstance();
        manager.beginTransaction().replace(layoutId, fragment)
                .commitAllowingStateLoss();
        return fragment;
    }


}

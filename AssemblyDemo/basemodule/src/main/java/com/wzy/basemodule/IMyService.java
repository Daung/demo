package com.wzy.basemodule;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public interface IMyService {
    void goMyActivity(Context context);
    Fragment getLoginFragment(FragmentManager manager, int layoutId);
}

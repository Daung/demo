package com.wzy.iocdemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;



public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectManager.inject(this);
    }
}

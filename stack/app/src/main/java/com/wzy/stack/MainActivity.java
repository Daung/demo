package com.wzy.stack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test();
    }

    private void test() {
        CustomArrayStack stack = new CustomArrayStack();
        for (int i = 0; i < 100; i++) {
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            Object pop = stack.pop();
            Log.d(TAG, "test: pop = " + pop);
        }


    }
}

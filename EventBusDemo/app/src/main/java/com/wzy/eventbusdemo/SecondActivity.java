package com.wzy.eventbusdemo;

import android.os.Bundle;
import android.view.View;

public class SecondActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

    }


    public void sendMessage(View view) {
        postMessage();

    }

    private void postMessage() {
        Person person = new Person("王子悦", 5);
        EventBus.getDefault().post(person);
    }

    private Runnable threadRunnable = new Runnable() {
        @Override
        public void run() {
            postMessage();
        }
    };

    public void sendThreadMessage(View view) {
        ThreadPoolManager.getInstance().addTask(threadRunnable);
    }
}

package com.wzy.eventbusdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goSecondActivity(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }

    @Subscribe(threadMode = ThreadMode.main)
    public void getPerson(Person person) {
        Toast.makeText(this, person.toString(), Toast.LENGTH_SHORT).show();
    }
}

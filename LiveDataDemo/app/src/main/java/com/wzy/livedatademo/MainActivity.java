package com.wzy.livedatademo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class MainActivity extends AppCompatActivity {

   static MutableLiveData<String> liveData;
    private TextView mContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContent = findViewById(R.id.tv_content);

        initLiveData();

    }

    private void initLiveData() {
        if (liveData == null) {
            liveData = new MutableLiveData<>();
            liveData.observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    mContent.setText(s);
                    Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void goActivity(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }

    public void sendData(View view) {
        liveData.setValue("aaaaa" + Thread.currentThread().getName());
    }

    public void sendThreadData(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                liveData.postValue("bbbbb" + Thread.currentThread().getName());
            }
        }).start();
    }
}

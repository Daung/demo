package com.wzy.viewmodledemo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.wzy.livedatademo.R;

public class LiveDataActivity extends AppCompatActivity {

    private LiveDataViewModel liveDataViewModel;
    private TextView mNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data);
        mNumber = findViewById(R.id.tv_numer);

        liveDataViewModel = new ViewModelProvider(this).get(LiveDataViewModel.class);
        liveDataViewModel.getLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mNumber.setText(String.valueOf(integer));
            }
        });
    }

    public void addNumber(View view) {
        liveDataViewModel.addNumber(1);
    }
}

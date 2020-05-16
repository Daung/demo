package com.wzy.managerdemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.wzy.helper.ViewModelManager;
import com.wzy.livedatademo.R;
import com.wzy.viewmodel.IntegerViewModel;

public class ManagerActivity extends AppCompatActivity {

    public static final String TEST_KEY = "test_key";
    private static final String TAG = "ManagerActivity";

    private TextView mContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        mContent = findViewById(R.id.tv_content);
        ViewModelManager.of().bindActivity(this, TEST_KEY, IntegerViewModel.class);
        IntegerViewModel viewModel = ViewModelManager.of().getViewModel(TEST_KEY);
        viewModel.getLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mContent.setText(String.valueOf(integer));
                Log.d(TAG, "onChanged: integer = " + integer);

            }
        });

    }

    public void addOnClick(View view) {
        IntegerViewModel viewModel = ViewModelManager.of().getViewModel(TEST_KEY);
        viewModel.setNumber(1);
    }

    public void goTwoActivity(View view) {
       startActivity(new Intent(this, FourActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewModelManager.of().removeViewModel(TEST_KEY);
    }
}

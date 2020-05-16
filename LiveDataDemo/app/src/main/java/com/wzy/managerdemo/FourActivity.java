package com.wzy.managerdemo;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wzy.helper.ViewModelManager;
import com.wzy.livedatademo.R;
import com.wzy.viewmodel.IntegerViewModel;

public class FourActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);
    }

    public void invokeIntegerViewModel(View view) {
        IntegerViewModel viewModel = ViewModelManager.of().getViewModel(ManagerActivity.TEST_KEY);
        viewModel.getLiveData().setValue(10);

    }
}

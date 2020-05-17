package com.wzy.managerdemo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;

import com.wzy.helper.ViewModelManager;
import com.wzy.livedatademo.BR;
import com.wzy.livedatademo.R;
import com.wzy.viewmodel.SaveViewModel;

public class SaveHandlerActivity extends AppCompatActivity {

    private static final String VIEW_MODEL_KEY = "view_model_key";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_save);

        ViewModelManager.of().bindSavedActivity(this, VIEW_MODEL_KEY,
                SaveViewModel.class);

        ViewModel viewModel = ViewModelManager.of().getViewModel(VIEW_MODEL_KEY);

        viewDataBinding.setVariable(BR.save, viewModel);
        viewDataBinding.setLifecycleOwner(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewModelManager.of().removeFactory(this);
        ViewModelManager.of().removeViewModel(VIEW_MODEL_KEY);
    }
}

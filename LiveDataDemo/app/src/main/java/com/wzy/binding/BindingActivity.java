package com.wzy.binding;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;

import com.wzy.helper.ViewModelManager;
import com.wzy.livedatademo.R;
import com.wzy.viewmodel.IntegerViewModel;

import static com.wzy.livedatademo.BR.integerViewModel;

public class BindingActivity extends AppCompatActivity {
    private static final String VAR_ITEM = "var_item";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelManager.of().bindActivity(this, VAR_ITEM, IntegerViewModel.class);
        ViewModel viewModel = ViewModelManager.of().getViewModel(VAR_ITEM);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_binding);
        viewDataBinding.setVariable(integerViewModel, viewModel);
        viewDataBinding.setLifecycleOwner(this);


    }
}

package com.wzy.viewmodledemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.wzy.livedatademo.R;
import com.wzy.viewmodel.MyViewModel;

public class ViewModelActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private TextView textView;
    private MyViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_three);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        button.setOnClickListener(this);

        viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        textView.setText(String.valueOf(viewModel.getNumber()));

    }

    @Override
    public void onClick(View v) {
      int number = viewModel.getNumber();
      viewModel.setNumber(++number);
      textView.setText(String.valueOf(viewModel.getNumber()));
    }
}

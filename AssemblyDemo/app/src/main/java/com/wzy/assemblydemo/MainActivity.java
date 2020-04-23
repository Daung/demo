package com.wzy.assemblydemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.wzy.basemodule.ServiceFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btn1:
                    ServiceFactory.getInstance().getMyService().goMyActivity(this);
                    break;
                case R.id.btn2:
                    ServiceFactory.getInstance().getLoginService().goLoginActivity(this);
                    break;

                case R.id.btn3:
                    Fragment fragment = ServiceFactory.getInstance().getMyService().
                            getLoginFragment(getSupportFragmentManager(), R.id.fl_content);
                    Toast.makeText(this, fragment.toString(), Toast.LENGTH_SHORT).show();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

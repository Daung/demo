package com.wzy.iocdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: btn1 = " + btn1.toString());
        Log.d(TAG, "onCreate: btn2 = " + btn2.toString());



    }

    @OnClick({R.id.btn1, R.id.btn2})
    public void execClick(View view) {
        Button button = (Button) view;
        Toast.makeText(this, button.getText(), Toast.LENGTH_SHORT).show();

    }

    @OnLongClick({R.id.btn1, R.id.btn2})
    public boolean execLongClick(View view) {
        Button button = (Button) view;
        Toast.makeText(this, "长按了 " + button.getText(), Toast.LENGTH_SHORT).show();
        return true;
    }
}

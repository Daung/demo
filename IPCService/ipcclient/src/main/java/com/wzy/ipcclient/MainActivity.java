package com.wzy.ipcclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wzy.ipcservice.IPersonService;
import com.wzy.ipcservice.Person;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iPersonService = IPersonService.Stub.asInterface(service);
            showToast("服务器连接成功");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            showToast("断开连接");
        }
    };

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private IPersonService iPersonService;

    private TextView mContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContent = findViewById(R.id.tv_content);
    }


    public void startService(View view) {
        Intent intent = new Intent("android.intent.action.person");
        intent.setPackage("com.wzy.ipcservice");
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    public void addPerson(View view) {
        if (iPersonService != null) {
            try {
                iPersonService.addPerson(new Person("wzy", 5));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void getPersonList(View view) {
        if (iPersonService != null) {
            try {
                List<Person> personList = iPersonService.getPersonList();
                StringBuilder builder = new StringBuilder();
                for (Person person : personList) {
                    builder.append(person.toString()).append("\n");
                }
                mContent.setText(builder.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}

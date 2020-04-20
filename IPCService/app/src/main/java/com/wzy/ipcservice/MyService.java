package com.wzy.ipcservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.List;

public class MyService extends Service {

    private List<Person> personList = new ArrayList<>();

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


    IBinder binder = new IPersonService.Stub() {
        @Override
        public void addPerson(Person person) throws RemoteException {
            personList.add(person);
        }

        @Override
        public List<Person> getPersonList() throws RemoteException {
            return personList;
        }
    };


}

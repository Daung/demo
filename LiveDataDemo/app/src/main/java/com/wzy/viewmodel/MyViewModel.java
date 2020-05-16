package com.wzy.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {


    private MutableLiveData<Integer> liveData;

    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public MutableLiveData<Integer> getLiveData() {
        checkNonNullLiveData();
        return liveData;
    }




    private void checkNonNullLiveData() {
        if (liveData == null) {
            liveData = new MutableLiveData<>();
            liveData.setValue(0);
        }
    }
}

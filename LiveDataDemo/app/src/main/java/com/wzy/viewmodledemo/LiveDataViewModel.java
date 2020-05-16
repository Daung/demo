package com.wzy.viewmodledemo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LiveDataViewModel extends ViewModel {

    private MutableLiveData<Integer> liveData;

    public MutableLiveData<Integer> getLiveData() {
        checkNullData();
        return liveData;
    }

    public void addNumber(Integer number) {
        checkNullData();
        Integer value = liveData.getValue();
        liveData.setValue(value + number);
    }

    private void checkNullData() {
        if (liveData == null) {
            liveData = new MutableLiveData<>();
            liveData.setValue(0);
        }
    }
}

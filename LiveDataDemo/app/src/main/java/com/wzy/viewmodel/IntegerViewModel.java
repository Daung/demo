package com.wzy.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class IntegerViewModel extends ViewModel {
    private MutableLiveData<Integer> liveData;

    public MutableLiveData<Integer> getLiveData() {
        checkNonNullData();
        return liveData;
    }

    private void checkNonNullData() {
        if (liveData == null) {
            liveData = new MutableLiveData<>();
            liveData.setValue(0);
        }
    }

    public void setNumber(int count) {
        checkNonNullData();
        Integer value = liveData.getValue();
        liveData.setValue(value + count);
    }
}

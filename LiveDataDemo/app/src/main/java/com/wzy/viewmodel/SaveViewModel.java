package com.wzy.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

public class SaveViewModel extends ViewModel {
    public static final String SAVE_ITEM = "save_item";

    private final SavedStateHandle handle;

    public SaveViewModel(SavedStateHandle handle) {
       this.handle = handle;
    }


    public MutableLiveData<Integer> getNumber() {
        if (!handle.contains(SAVE_ITEM)) {
            handle.set(SAVE_ITEM, 0);
        }
       return handle.getLiveData(SAVE_ITEM);
    }


    public void add() {
        Object value = handle.get(SAVE_ITEM);
        handle.set(SAVE_ITEM, Integer.parseInt(value.toString()) + 1);
    }



}

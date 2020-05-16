package com.wzy.helper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.HashMap;
import java.util.Map;

public class ViewModelManager {
    private static ViewModelManager instance;

    private Map<String, ViewModel> viewModels = new HashMap<>();


    public static ViewModelManager of() {
        if (instance == null) {
            synchronized (ViewModelManager.class) {
                if (instance == null) {
                    instance = new ViewModelManager();
                }
            }
        }
        return instance;
    }



    private ViewModelProvider createViewModelProvider(@NonNull FragmentActivity activity) {
        return new ViewModelProvider(activity);
    }

    private ViewModelProvider createViewModelProvider(@NonNull Fragment fragment) {
        return new ViewModelProvider(fragment);
    }

    public <T extends ViewModel> void bindActivity(@NonNull FragmentActivity activity, @NonNull String name, Class<T> aClass) {
        ViewModel viewModel = viewModels.get(name);
        if (viewModel == null) {
            addViewModel(createViewModelProvider(activity), name, aClass);
        }
    }

    public <T extends ViewModel> void bindFragment(@NonNull Fragment fragment, @NonNull String name, Class<T> aClass) {
        ViewModel viewModel = viewModels.get(name);
        if (viewModel != null) {
            addViewModel(createViewModelProvider(fragment), name, aClass);
        }
    }


    private <T extends ViewModel> void addViewModel(ViewModelProvider provider, @NonNull String name, Class<T> aClass) {
        T newViewModel = provider.get(name, aClass);
        viewModels.put(name, newViewModel);
    }

    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T getViewModel(@NonNull String name) {
        return (T) viewModels.get(name);
    }


    public void removeViewModel(@NonNull String name) {
        viewModels.remove(name);
    }


    public void clear() {
        viewModels.clear();
    }


}

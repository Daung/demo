package com.wzy.helper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.wzy.livedatademo.MyApplication;

import java.util.HashMap;
import java.util.Map;

public class ViewModelManager {
    private static ViewModelManager instance;

    private Map<String, ViewModel> viewModels = new HashMap<>();
    private Map<Object, SavedStateViewModelFactory> factoryMap = new HashMap<>();


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
        return createViewModelProvider(activity, null);
    }

    private ViewModelProvider createViewModelProvider(@NonNull Fragment fragment) {
        return createViewModelProvider(fragment, null);
    }


    private ViewModelProvider createViewModelProvider(@NonNull FragmentActivity activity, ViewModelProvider.Factory factory) {
        if (factory != null) {
            return new ViewModelProvider(activity, factory);
        }
        return new ViewModelProvider(activity);
    }

    private ViewModelProvider createViewModelProvider(@NonNull Fragment fragment, ViewModelProvider.Factory factory) {
        if (factory != null) {
            return new ViewModelProvider(fragment, factory);
        }
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
        if (viewModel == null) {
            addViewModel(createViewModelProvider(fragment), name, aClass);
        }
    }


    public <T extends ViewModel> void bindSavedActivity(@NonNull FragmentActivity activity,
                                                        @NonNull String name,
                                                        Class<T> aClass) {
        ViewModel viewModel = viewModels.get(name);
        if (viewModel == null) {
            checkNonNullFactory(activity);
            addViewModel(createViewModelProvider(activity, factoryMap.get(activity)), name, aClass);
        }
    }


    public <T extends ViewModel> void bindSavedFragment(@NonNull Fragment fragment,
                                                        @NonNull String name,
                                                        Class<T> aClass) {
        ViewModel viewModel = viewModels.get(name);
        if (viewModel != null) {
            checkNonNullFactory(fragment);
            addViewModel(createViewModelProvider(fragment, factoryMap.get(fragment)), name, aClass);
        }
    }

    private void checkNonNullFactory(@NonNull FragmentActivity activity) {
        SavedStateViewModelFactory factory = factoryMap.get(activity);
        if (factory == null) {
            factory = new SavedStateViewModelFactory(MyApplication.of(), activity);
            factoryMap.put(activity, factory);
        }
    }

    private void checkNonNullFactory(@NonNull Fragment fragment) {
        SavedStateViewModelFactory factory = factoryMap.get(fragment);
        if (factory == null) {
            factory = new SavedStateViewModelFactory(MyApplication.of(), fragment);
            factoryMap.put(fragment, factory);
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

    public void removeFactory(@NonNull Object object) {
        factoryMap.remove(object);
    }


    public void clear() {
        viewModels.clear();
    }


}

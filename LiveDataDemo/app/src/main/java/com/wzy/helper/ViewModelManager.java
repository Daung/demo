package com.wzy.helper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.SavedStateViewModelFactory;
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
        bindActivity(activity, name, aClass, null);
    }

    public <T extends ViewModel> void bindFragment(@NonNull Fragment fragment, @NonNull String name, Class<T> aClass) {
        bindFragment(fragment, name, aClass, null);
    }

    public <T extends ViewModel> void bindActivity(@NonNull FragmentActivity activity,
                                                   @NonNull String name,
                                                   Class<T> aClass,
                                                   SavedStateViewModelFactory factory) {
        ViewModel viewModel = viewModels.get(name);
        if (viewModel == null) {
            addViewModel(createViewModelProvider(activity, factory), name, aClass);
        }
    }

    public <T extends ViewModel> void bindFragment(@NonNull Fragment fragment,
                                                   @NonNull String name,
                                                   Class<T> aClass,
                                                   SavedStateViewModelFactory factory) {
        ViewModel viewModel = viewModels.get(name);
        if (viewModel != null) {
            addViewModel(createViewModelProvider(fragment, factory), name, aClass);
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

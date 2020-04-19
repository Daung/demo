package com.wzy.eventbusdemo;

import android.os.Looper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EventBus {

    private static volatile EventBus eventBus;

    private Map<Object, List<AnnotationMethod>> listMap;

    private EventBus() {
        if (listMap == null) {
            listMap = new HashMap<>();
        }
    }


    public static EventBus getDefault() {
        if (eventBus == null) {
            synchronized (EventBus.class) {
                if (eventBus == null) {
                    eventBus = new EventBus();
                }
            }
        }
        return eventBus;
    }

    public void register(Object object) {
        List<AnnotationMethod> annotationMethods = listMap.get(object);
        if (annotationMethods == null) {
            findAnnotationMethod(object);
        }
    }

    private void findAnnotationMethod(Object object) {
        List<AnnotationMethod> annotationMethods = null;
        Class<?> aClass = object.getClass();

        while (true) {
            if (aClass == null || aClass.getName().startsWith("java") ||
                    aClass.getName().startsWith("javax") ||
                    aClass.getName().startsWith("android") ||
                    aClass.getName().startsWith("androidx") ||
                    aClass.getName().startsWith("sun")) {
                break;
            }
            Method[] declaredMethods = aClass.getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                Subscribe annotation = declaredMethod.getAnnotation(Subscribe.class);
                if (annotation == null) {
                    continue;
                }
                Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
                if (parameterTypes.length > 1) {
                    throw new IllegalArgumentException("参数的个数不能大于1个");
                }
                if (annotationMethods == null) {
                    annotationMethods = new ArrayList<>();
                }
                AnnotationMethod annotationMethod = new
                        AnnotationMethod(annotation, declaredMethod, parameterTypes[0]);
                annotationMethods.add(annotationMethod);
            }
            aClass = aClass.getSuperclass();
        }
        if (annotationMethods != null) {
            listMap.put(object, annotationMethods);
        }
    }

    public void unRegister(Object object) {
        listMap.remove(object);
    }

    public void post(Object param) {
        Set<Object> objects = listMap.keySet();
        for (Object object : objects) {

            List<AnnotationMethod> annotationMethods = listMap.get(object);
            if (annotationMethods == null) {
                continue;
            }
            for (AnnotationMethod annotationMethod : annotationMethods) {
                if (annotationMethod.getType().isAssignableFrom(param.getClass())) {
                    postMessage(param, annotationMethod, object);
                }
            }
        }
    }


    private boolean isMainLooper() {
        return Looper.getMainLooper().equals(Looper.myLooper());
    }


    private void postMessage(final Object params, final AnnotationMethod annotationMethod, final Object object) {
        switch (annotationMethod.getSubscribe().threadMode()) {
            case main:
                handlerMainLogic(params, annotationMethod, object);
                break;
            case background:
                handlerBackgroundLogic(params, annotationMethod, object);
                break;
        }
    }

    private void handlerMainLogic(Object params, AnnotationMethod annotationMethod, Object object) {
        if (isMainLooper()) {
            invokeMethod(params, annotationMethod, object);
        } else {
            MyMainRunnable mainRunnable = new MyMainRunnable.Builder()
                    .setParams(params)
                    .setAnnotationMethod(annotationMethod)
                    .setObject(object)
                    .builder();
            ThreadPoolManager.getInstance().addTask(mainRunnable);
        }
    }

    private void invokeMethod(Object params, AnnotationMethod annotationMethod, Object object) {
        try {
            annotationMethod.getMethod().invoke(object, params);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void handlerBackgroundLogic(Object params, AnnotationMethod annotationMethod, Object object) {
        if (isMainLooper()) {
            MyThreadRunnable threadRunnable = new MyThreadRunnable.Builder()
                    .setParams(params)
                    .setAnnotationMethod(annotationMethod)
                    .setObject(object)
                    .builder();
            ThreadPoolManager.getInstance().addTask(threadRunnable);
        } else {
            invokeMethod(params, annotationMethod, object);
        }
    }
}

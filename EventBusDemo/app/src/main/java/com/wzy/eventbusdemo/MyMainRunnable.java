package com.wzy.eventbusdemo;

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.InvocationTargetException;

public class MyMainRunnable implements Runnable {
    private final MyMainRunnable.Builder builder;
    private Handler handler = new Handler(Looper.getMainLooper());

    public MyMainRunnable(MyMainRunnable.Builder builder) {
        this.builder = builder;
    }


    @Override
    public void run() {
        postMainRunnable(builder.params, builder.annotationMethod, builder.object);
    }

    public static class Builder {
        private Object params;
        private AnnotationMethod annotationMethod;
        private Object object;

        public MyMainRunnable.Builder setParams(Object params) {
            this.params = params;
            return this;
        }

        public MyMainRunnable.Builder setAnnotationMethod(AnnotationMethod annotationMethod) {
            this.annotationMethod = annotationMethod;
            return this;
        }

        public MyMainRunnable.Builder setObject(Object object) {
            this.object = object;
            return this;
        }

        MyMainRunnable builder() {
            return new MyMainRunnable(this);
        }
    }


    private void postMainRunnable(final Object params, final AnnotationMethod annotationMethod, final Object object) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    annotationMethod.getMethod().invoke(object, params);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

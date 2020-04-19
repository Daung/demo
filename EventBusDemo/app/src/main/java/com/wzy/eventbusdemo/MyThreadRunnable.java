package com.wzy.eventbusdemo;

import java.lang.reflect.InvocationTargetException;

public class MyThreadRunnable implements Runnable {
    private final MyThreadRunnable.Builder builder;

    public MyThreadRunnable(MyThreadRunnable.Builder builder) {
        this.builder = builder;
    }


    @Override
    public void run() {
        postMyRunnable(builder.params, builder.annotationMethod, builder.object);
    }

    public static class Builder {
        private Object params;
        private AnnotationMethod annotationMethod;
        private Object object;

        public MyThreadRunnable.Builder setParams(Object params) {
            this.params = params;
            return this;
        }

        public MyThreadRunnable.Builder setAnnotationMethod(AnnotationMethod annotationMethod) {
            this.annotationMethod = annotationMethod;
            return this;
        }

        public MyThreadRunnable.Builder setObject(Object object) {
            this.object = object;
            return this;
        }

        MyThreadRunnable builder() {
            return new MyThreadRunnable(this);
        }
    }


    private void postMyRunnable(final Object params, final AnnotationMethod annotationMethod, final Object object) {
        try {
            annotationMethod.getMethod().invoke(object, params);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

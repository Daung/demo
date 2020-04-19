package com.wzy.eventbusdemo;

import java.lang.reflect.Method;

public class AnnotationMethod {
    private Subscribe subscribe;
    private Method method;
    private Class<?> type;

    public AnnotationMethod(Subscribe subscribe, Method method, Class<?> type) {
        this.subscribe = subscribe;
        this.method = method;
        this.type = type;
    }

    public Subscribe getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Subscribe subscribe) {
        this.subscribe = subscribe;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }
}

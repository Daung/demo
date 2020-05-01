package com.wzy.iocdemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CustomInvocation implements InvocationHandler {

    private Object target;
    private Method method;

    public CustomInvocation(Object target, Method method) {
        this.target = target;
        this.method = method;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return this.method.invoke(target, args);
    }
}

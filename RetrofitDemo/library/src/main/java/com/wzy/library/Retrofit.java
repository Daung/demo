package com.wzy.library;

import android.text.TextUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Retrofit {
    private final String url;


    private Map<Method, ServiceMethod> methodMap = new HashMap<>();

    public Retrofit(Builder builder) {
        this.url = builder.url;
    }

    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> aClass) {
        return (T) Proxy.newProxyInstance(aClass.getClassLoader(), new Class[]{aClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //打印proxy

                System.out.println(proxy.getClass());

                //打印方法名
                System.out.println(method.getName());

                //获取方法的注解
              ServiceMethod serviceMethod =  loadServiceMethod(method);


                //打印参数

                System.out.println(Arrays.toString(args));


                return null;
            }
        });
    }

    private ServiceMethod loadServiceMethod(Method method) {
        ServiceMethod serviceMethod = methodMap.get(method);
        if (serviceMethod != null) {
            return serviceMethod;
        }
        serviceMethod = methodMap.get(method);
        synchronized (methodMap) {
            if (serviceMethod == null) {
                serviceMethod = new ServiceMethod.Builder(this,method).build();
                methodMap.put(method, serviceMethod);
            }
        }
        return serviceMethod;
    }

    private ServiceMethod makeServiceMethod(Method method) {
        Annotation[] annotations = method.getAnnotations();
        if (annotations.length <= 0) {
            return null;
        }

        for (Annotation annotation : annotations) {
            parseMethodAnnotation(annotation);
        }
        return null;
    }

    private void parseMethodAnnotation(Annotation annotation) {
        if (annotation instanceof GET) {
            GET get = (GET) annotation;
            String path = get.value();
        }
    }

    public static final class Builder {

        private String url;

        public Builder baseUrl(String url) {
            if (url == null || "".equals(url)) {
                throw new NullPointerException("url 不能为空");
            }
            this.url = url;
            return this;
        }

        public Retrofit build() {
            return new Retrofit(this);
        }
    }
}

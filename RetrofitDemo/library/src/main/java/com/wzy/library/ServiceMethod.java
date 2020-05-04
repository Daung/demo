package com.wzy.library;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class ServiceMethod {

    private Method method;
    private Annotation[] methodAnnotation;
    private Annotation[][] parametersAnnotation;
    private boolean hasBody;


    public ServiceMethod(Builder builder) {
        this.method = builder.method;
        this.methodAnnotation = builder.methodAnnotation;
        this.parametersAnnotation = builder.parametersAnnotation;
        this.hasBody = builder.hasBody;
    }

    final static class Builder {
        private final Retrofit retrofit;
        private final Method method;
        private Annotation[] methodAnnotation;
        private Annotation[][] parametersAnnotation;
        private ParameterHandler[] parameterhanders;
        private boolean hasBody;
        private String httpMethod;
        private String relativeUrl;

        private String realUrl;

        public Builder(Retrofit retrofit, Method method) {
            this.retrofit = retrofit;
            this.method = method;
            this.methodAnnotation = method.getAnnotations();
            this.parametersAnnotation = method.getParameterAnnotations();

        }


        public ServiceMethod build() {
            for (Annotation annotation : methodAnnotation) {
                parseMethodAnnotation(annotation);
            }

            parameterhanders = new ParameterHandler[parametersAnnotation.length];

            for (int i = 0; i < parametersAnnotation.length; i++) {
                Annotation[] parameterAnnotation = parametersAnnotation[i];
                if (parameterAnnotation == null) {
                    throw new IllegalArgumentException("不符合参数规定");
                }
                parameterhanders[i] = parseParameterAnnotation(parameterAnnotation);
            }



            return new ServiceMethod(this);
        }

        private ParameterHandler parseParameterAnnotation(Annotation[] annotations) {
            ParameterHandler result = null;
            for (Annotation annotation : annotations) {
                ParameterHandler parameterHandler = parseAnnotationParameter(annotation);
                if (parameterHandler == null) {
                    continue;
                }
                result = parameterHandler;
            }
            if (result == null) {
                throw new IllegalArgumentException("没有查到retrofit注解");
            }

            return result;
        }

        //解析参数注解的值
        private static ParameterHandler parseAnnotationParameter(Annotation annotation) {
            ParameterHandler parameterHandler = null;
            if (annotation instanceof Query) {
                parameterHandler = new ParameterHandler.Query(((Query) annotation).value());
            } else if (annotation instanceof Field) {
                parameterHandler = new ParameterHandler.Field(((Field) annotation).value());
            }
            return parameterHandler;
        }

        private void parseMethodAnnotation(Annotation annotation) {
            if (annotation instanceof GET) {
                GET get = (GET) annotation;
                parseHttpMethodAndpath("GET", get.value(), false);
            } else if (annotation instanceof POST) {
                POST post = (POST) annotation;
                parseHttpMethodAndpath("POST", post.value(), true);
            }

        }

        private void parseHttpMethodAndpath(String httpMethod, String value, boolean hasBody) {
            this.httpMethod = httpMethod;
            this.relativeUrl = value;
            this.hasBody = hasBody;
        }
    }


}

package com.wzy.iocdemo;

import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class InjectManager {
    private static final String TAG = "InjectManager";


    public static void inject(Object object) {
        handlerSetContentView(object);
        handlerFindView(object);
        handlerClickListener(object);
    }

    private static void handlerFindView(Object object) {
        try {
            Class<?> aClass = object.getClass();
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                BindView annotation = declaredField.getAnnotation(BindView.class);
                if (annotation == null) {
                    continue;
                }
                int id = annotation.value();
                Method findViewById = aClass.getMethod("findViewById", int.class);
                Object view = findViewById.invoke(object, id);
                declaredField.setAccessible(true);
                declaredField.set(object, view);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handlerClickListener(final Object object) {
        Class<?> aClass = object.getClass();
        Method[] declaredMethods = aClass.getDeclaredMethods();
        for (final Method declaredMethod : declaredMethods) {
            try {
                Annotation[] annotations = declaredMethod.getAnnotations();
                if (annotations.length <= 0) {
                    continue;
                }

                for (Annotation annotation : annotations) {

                    Class<? extends Annotation> annotationType = annotation.annotationType();
                    if (annotationType == null) {
                        continue;
                    }



                    OnItemClickListener onItemClickListener = annotationType.getAnnotation(OnItemClickListener.class);
                    //自我感觉 如果不是多态注解，使用这种方式获取注解
                    onItemClickListener = declaredMethod.getAnnotation(OnItemClickListener.class);
                    if (onItemClickListener != null) {
                        int[] ids = onItemClickListener.value();
                        for (int id : ids) {
                            Method findViewById = aClass.getMethod("findViewById", int.class);
                            Object view = findViewById.invoke(object, id);
                            if (!(view instanceof RecyclerView)) {
                                throw new IllegalArgumentException("must be recycler view id");
                            }

                            final RecyclerView recyclerView = (RecyclerView) view;
                            RecyclerViewOnItemClickListener<Person> listener = new RecyclerViewOnItemClickListener<>
                                    (recyclerView, new RecyclerViewOnItemClickListener.OnItemClickListener<Person>() {
                                        @Override
                                        public void onItemClick(View view, Person data, int position) {
                                            try {
                                                declaredMethod.invoke(object, view, data, position);
                                            } catch (IllegalAccessException | InvocationTargetException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                            recyclerView.addOnItemTouchListener(listener);
                        }
                    }

                    OnItemLongClickListener onLongClickListener = declaredMethod.getAnnotation(OnItemLongClickListener.class);
                    if (onLongClickListener != null) {
                        int[] ids = onLongClickListener.value();
                        for (int id : ids) {
                            Method findViewById = aClass.getMethod("findViewById", int.class);
                            Object view = findViewById.invoke(object, id);
                            if (!(view instanceof RecyclerView)) {
                                throw new IllegalArgumentException("must be recycler view id");
                            }

                            final RecyclerView recyclerView = (RecyclerView) view;
                            RecyclerViewOnItemClickListener<Person> listener = new RecyclerViewOnItemClickListener<>
                                    (recyclerView, new RecyclerViewOnItemClickListener.OnItemLongClickListener<Person>() {
                                        @Override
                                        public void onLongItemClick(View view, Person data, int position) {
                                            try {
                                                declaredMethod.invoke(object, view, data, position);
                                            } catch (IllegalAccessException | InvocationTargetException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                            recyclerView.addOnItemTouchListener(listener);
                        }
                    }

                    //自我感觉   注解多态 使用这种方法获取注解
                    BaseEvent baseEvent = annotationType.getAnnotation(BaseEvent.class);
                    if (baseEvent == null) {
                        continue;
                    }

                    //View.onClickListener
                    Class listenerType = baseEvent.listenerType();
                    String setListener = baseEvent.setListener();
                    String callbackMethod = baseEvent.methodName();
                    Method value = annotation.getClass().getMethod("value");
                    int[] ids = (int[]) value.invoke(annotation);
                    if (ids == null) {
                        break;
                    }
                    for (int id : ids) {
                        Log.d(TAG, "handlerClickListener: id = " + id);

                        Method findViewById = aClass.getMethod("findViewById", int.class);
                        Object view = findViewById.invoke(object, id);
                        if (view == null) {
                            continue;
                        }

                        Method setOnClickListener = view.getClass().getMethod(setListener, listenerType);
                        CustomInvocation customInvocation = new CustomInvocation(object, declaredMethod);
                        Object onClickListener = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType}, customInvocation);
                        setOnClickListener.invoke(view, onClickListener);

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private static void handlerSetContentView(Object object) {
        try {
            Class<?> aClass = object.getClass();
            ContentView annotation = aClass.getAnnotation(ContentView.class);
            if (annotation == null) {
                return;
            }

            int value = annotation.value();
            if (value == -1) {
                return;
            }

            Method setContentView = aClass.getMethod("setContentView", int.class);
            setContentView.invoke(object, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

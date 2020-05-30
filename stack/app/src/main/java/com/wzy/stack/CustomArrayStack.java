package com.wzy.stack;

import java.util.Arrays;

public class CustomArrayStack {

    private Object[] elementData = null;

    private static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;


    private static final int MAX_CAPACITY = 1 << 30;


    private static int ELEMENT_TOP = -1;


    private int mInitialCapacity;



    public CustomArrayStack() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public CustomArrayStack(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("容器的长度为 " + capacity);
        } else {
            this.mInitialCapacity = Math.min(capacity, MAX_CAPACITY);
        }

    }


    public synchronized void push(Object object) {
        checkoutArrayNotNull();

        if (ELEMENT_TOP > MAX_CAPACITY) {
            throw new IndexOutOfBoundsException("容器已满，不能再添加数据");
        } else if (ELEMENT_TOP < elementData.length - 1) {
            elementData[++ELEMENT_TOP] = object;
        } else {
            elementData = Arrays.copyOf(elementData, DEFAULT_INITIAL_CAPACITY  + elementData.length);
            elementData[++ELEMENT_TOP] = object;
        }
    }

    public synchronized Object pop() {
        if (isEmpty()) {
            throw new NullPointerException("容器还没有初始化，不能进行pop操作");
        }

        return elementData[ELEMENT_TOP--];
    }


    public boolean isEmpty() {
        return ELEMENT_TOP == -1;
    }

    private synchronized void checkoutArrayNotNull() {
        if (elementData == null) {
            elementData = new Object[mInitialCapacity];
        }
    }
}

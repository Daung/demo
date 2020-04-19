package com.wzy.eventbusdemo;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager {

    private static volatile ThreadPoolManager manager;
    private final ThreadPoolExecutor threadPoolExecutor;

    private LinkedBlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>();

    public static ThreadPoolManager getInstance() {
        if (manager == null) {
            synchronized (ThreadPoolManager.class) {
                if (manager == null) {
                    manager = new ThreadPoolManager();
                }
            }
        }
        return manager;
    }

    private ThreadPoolManager() {
        threadPoolExecutor = new ThreadPoolExecutor(3, 10, 5,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                blockingQueue.add(r);
            }
        });
        threadPoolExecutor.execute(coreRunnable);
    }


    private Runnable coreRunnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    Runnable runnable = blockingQueue.take();
                    threadPoolExecutor.execute(runnable);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public void addTask(Runnable runnable) {
        try {
            blockingQueue.put(runnable);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

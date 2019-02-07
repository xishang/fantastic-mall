package com.fantastic.mall.web.demo;

import rx.Observable;
import rx.Observer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author xishang
 * @version 1.0
 * @since 2018/5/2
 */
public class ShutdownHookDemo {


    public static void main(String[] args) {
//        shutdownHook();
        rxjava();
    }

    public static void shutdownHook() {
        // 执行任务
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        threadPool.execute(() -> {
            System.out.println("task start ...");
            try {
                Thread.sleep(3000);
            } catch (Exception e) {

            }
            System.out.println("task finished ...");
        });
        // JVM钩子
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("shutdown hook ...");
            threadPool.shutdown();
            try {
                threadPool.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("shutdown hook finished ...");
        }));
    }

    public static void rxjava() {
        Observer observer = new Observer() {
            @Override
            public void onCompleted() {
                System.out.println("completed");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("error");
            }

            @Override
            public void onNext(Object o) {
                System.out.println("next");
            }
        };

        Observable observable = Observable.create(new Observable.OnSubscribe() {
            @Override
            public void call(Object o) {
                System.out.println("call");
            }
        });

        observable.subscribe(observer);
    }

}

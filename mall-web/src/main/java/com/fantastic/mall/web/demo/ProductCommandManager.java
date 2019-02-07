package com.fantastic.mall.web.demo;

import rx.Observable;

import java.util.concurrent.Future;

/**
 * @author xishang
 * @version 1.0
 * @since 2018/5/2
 */
public class ProductCommandManager {

    public static void main(String[] args) {
        ProductCommand productCommand = new ProductCommand(ProductCommand.setter());
        // 同步调用
        String result = productCommand.execute();
        // 异步调用
        Future<String> future = productCommand.queue();
        // RxJava的Observable形式
        Observable<String> observable = productCommand.observe();
        observable.asObservable().subscribe(
                System.out::println
        );
    }

}

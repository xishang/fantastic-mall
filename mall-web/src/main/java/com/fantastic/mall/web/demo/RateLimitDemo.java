package com.fantastic.mall.web.demo;

import com.google.common.base.Charsets;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.google.common.util.concurrent.RateLimiter;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author xishang
 * @version 1.0
 * @since 2018/5/2
 */
public class RateLimitDemo {

    public static void main(String[] args) {
//        semaphore();
//        rateLimiter();
//        bloomFilter();
        completableFuture();
    }

    /**
     * JUC Semaphore: 漏斗算法, 利用信号量工具控制消费速度
     */
    public static void semaphore() {
        Semaphore semaphore = new Semaphore(2);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    // 获取信号量
                    semaphore.acquire();
                    System.out.println("获取到信号量, ms=" + System.currentTimeMillis());
                    Thread.sleep(2000);
                } catch (Exception e) {
                } finally {
                    semaphore.release();
                }
            }).start();
        }
    }

    /**
     * Guava RateLimiter: 令牌桶算法
     */
    public static void rateLimiter() {
        // 'SmoothBursty'模式, 每秒放2个令牌, 可以存储令牌(最大存储1秒的量), 允许预消费, 但下一次消费要等待
        RateLimiter burstyLimiter = RateLimiter.create(5.0D);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        System.out.printf("第1次消费1个令牌, 时间=%s, 需等待%f秒\n", System.currentTimeMillis(), burstyLimiter.acquire(1));
        System.out.printf("第2次消费1个令牌, 时间=%s, 需等待%f秒\n", System.currentTimeMillis(), burstyLimiter.acquire(1));
        System.out.printf("第3次消费1个令牌, 时间=%s, 需等待%f秒\n", System.currentTimeMillis(), burstyLimiter.acquire(1));
        System.out.printf("第4次消费1个令牌, 时间=%s, 需等待%f秒\n", System.currentTimeMillis(), burstyLimiter.acquire(1));
        System.out.printf("第5次消费1个令牌, 时间=%s, 需等待%f秒\n", System.currentTimeMillis(), burstyLimiter.acquire(1));
        System.out.printf("第6次消费1个令牌, 时间=%s, 需等待%f秒\n", System.currentTimeMillis(), burstyLimiter.acquire(1));
        System.out.printf("第7次消费1个令牌, 时间=%s, 需等待%f秒\n", System.currentTimeMillis(), burstyLimiter.acquire(1));
        System.out.printf("第8次消费1个令牌, 时间=%s, 需等待%f秒\n", System.currentTimeMillis(), burstyLimiter.acquire(1));
        System.out.println("--------------------");
        // 'SmoothWarmingUp'模型: 冷启动需要预热, 不能存储令牌, 允许预消费, 但下一次消费要等待, 且要经过预热时间才能达到平均速率(这里是2个每秒)
        RateLimiter warmingUpLimiter = RateLimiter.create(2.0D, 2, TimeUnit.SECONDS);
        System.out.printf("第1次消费1个令牌, 时间=%s, 需等待%f秒\n", System.currentTimeMillis(), warmingUpLimiter.acquire(1));
        System.out.printf("第2次消费1个令牌, 时间=%s, 需等待%f秒\n", System.currentTimeMillis(), warmingUpLimiter.acquire(1));
        System.out.printf("第3次消费1个令牌, 时间=%s, 需等待%f秒\n", System.currentTimeMillis(), warmingUpLimiter.acquire(1));
        System.out.printf("第4次消费1个令牌, 时间=%s, 需等待%f秒\n", System.currentTimeMillis(), warmingUpLimiter.acquire(1));
        System.out.printf("第5次消费1个令牌, 时间=%s, 需等待%f秒\n", System.currentTimeMillis(), warmingUpLimiter.acquire(1));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        System.out.printf("第1次消费1个令牌, 时间=%s, 需等待%f秒\n", System.currentTimeMillis(), warmingUpLimiter.acquire(1));
        System.out.printf("第2次消费1个令牌, 时间=%s, 需等待%f秒\n", System.currentTimeMillis(), warmingUpLimiter.acquire(1));
        System.out.printf("第3次消费1个令牌, 时间=%s, 需等待%f秒\n", System.currentTimeMillis(), warmingUpLimiter.acquire(1));
        System.out.printf("第4次消费1个令牌, 时间=%s, 需等待%f秒\n", System.currentTimeMillis(), warmingUpLimiter.acquire(1));
        System.out.printf("第5次消费1个令牌, 时间=%s, 需等待%f秒\n", System.currentTimeMillis(), warmingUpLimiter.acquire(1));
    }

    /**
     * Guava BloomFilter: 超大map过滤
     */
    public static void bloomFilter() {
        BloomFilter<String> existKeys = BloomFilter.create(
                Funnels.stringFunnel(Charsets.UTF_8),
                // 预计容量, 应尽量准确, 当插入数量接近容量时误判率会上升
                1024 * 1024,
                // 误判率
                0.0001D);
        for (int i = 0; i < 1024 * 512; i++) {
            existKeys.put("key-" + i);
        }
        System.out.println(existKeys.mightContain("key-1"));
        System.out.println(existKeys.mightContain("key--1"));
    }

    /**
     * Guava Intern
     */
    public static void stringIntern() {
        System.out.printf("ab = new(ab) ? %s\n", "ab" == new String("ab"));
        Interner<String> stringPool = Interners.newWeakInterner();
        String internStr = stringPool.intern("ab");
        String internNew = stringPool.intern(new String("ab"));
        System.out.printf("intern:ab = intern:new(ab) ? %s\n", internStr == internNew);
    }

    public static void completableFuture() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (Exception e) {

            }
            return "future1";
        });
        future.thenAcceptAsync(System.out::println);
        System.out.println("completable");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

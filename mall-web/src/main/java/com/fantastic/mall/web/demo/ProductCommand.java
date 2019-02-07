package com.fantastic.mall.web.demo;

import com.netflix.hystrix.*;

/**
 * @author xishang
 * @version 1.0
 * @since 2018/5/2
 */
public class ProductCommand extends HystrixCommand<String> {

    public ProductCommand(Setter setter) {
        super(setter);
    }

    public static Setter setter() {
        // 服务分组名称: 全局唯一
        HystrixCommandGroupKey commandGroupKey = HystrixCommandGroupKey.Factory.asKey("product");
        // 服务标识
        HystrixCommandKey commandKey = HystrixCommandKey.Factory.asKey("getProduct");
        // 线程池名称: 相同线程池名称的线程池是同一个, 若不配置默认为分组名
        HystrixThreadPoolKey threadPoolKey = HystrixThreadPoolKey.Factory.asKey("product-pool");
        // 线程池配置
        HystrixThreadPoolProperties.Setter threadPoolProperties = HystrixThreadPoolProperties.Setter()
                .withCoreSize(5) // 核心线程池大小
                .withKeepAliveTimeMinutes(5) // 空闲线程生存时间
                .withMaxQueueSize(20) // 线程池队列最大大小
                .withQueueSizeRejectionThreshold(20); // 当前队列大小
        // 命令属性配置
        HystrixCommandProperties.Setter commandProperties = HystrixCommandProperties.Setter()
                // 设置隔离策略为线程池隔离
                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD);
        return HystrixCommand.Setter
                .withGroupKey(commandGroupKey)
                .andCommandKey(commandKey)
                .andThreadPoolKey(threadPoolKey)
                .andThreadPoolPropertiesDefaults(threadPoolProperties)
                .andCommandPropertiesDefaults(commandProperties);
    }

    @Override
    protected String run() throws Exception {
        return null;
    }

}

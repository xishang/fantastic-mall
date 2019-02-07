package com.fantastic.mall.webgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class WebGatewayApplication {

    /**
     * 路由映射转换
     * - ?  匹配任意单个字符
     * - *  匹配任意数量的字符
     * - ** 匹配任意数量的字符, 支持多级目录
     *
     * @return
     */
    @Bean
    public PatternServiceRouteMapper routeMapper() {
        // 将"/name-v2"形式转换成"/v2/name"
        return new PatternServiceRouteMapper("(?<name>^.+)-(?<version>v.+$)",
                "${version}/${name}");
    }

    public static void main(String[] args) {
        SpringApplication.run(WebGatewayApplication.class, args);
    }
}

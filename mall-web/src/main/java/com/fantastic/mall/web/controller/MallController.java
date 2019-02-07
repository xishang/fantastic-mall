package com.fantastic.mall.web.controller;

import com.fantastic.mall.web.service.ProductService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author xishang
 * @version 1.0
 * @date 2018/4/8
 */
@RestController
@RequestMapping(value = "/mall")
public class MallController {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private ProductService productService;

    @HystrixCommand(fallbackMethod = "productsError")
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public Object getProductList() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://product-service/products", String.class);
        return response.getBody();
    }

    public String productsError() {
        return "Sorry, service is not available. -- by Hystrix";
    }

    @RequestMapping(value = "/products-feign", method = RequestMethod.GET)
    public Object getProductListByFeign() {
        return productService.getProductList();
    }

    @CacheResult
    @HystrixCommand(commandKey = "getProductCommand", fallbackMethod = "productsError")
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public Object getProduct(@CacheKey("id") @PathVariable Long id) {
        ResponseEntity<String> response = restTemplate.getForEntity("http://product-service/products", String.class);
        return response.getBody();
    }

    @CacheRemove(commandKey = "getProductCommand")
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public Object deleteProduct(@CacheKey("id") @PathVariable Long id) {
        ResponseEntity<String> response = restTemplate.getForEntity("http://product-service/products", String.class);
        return response.getBody();
    }

    @Value("${redis.host}")
    private String redisHost;

    @GetMapping(value = "/redis-host")
    public String getRedisHost() {
        return redisHost;
    }

}

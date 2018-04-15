package com.fantastic.mall.web.controller;

import com.fantastic.mall.web.service.ProductService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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

    @Value("${redis.host}")
    private String redisHost;

    @GetMapping(value = "/redis-host")
    public String getRedisHost() {
        return redisHost;
    }

}

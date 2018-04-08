package com.fantastic.mall.web.controller;

import org.springframework.http.ResponseEntity;
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

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public Object getProductList() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://product-service/products", String.class);
        return response.getBody();
    }

}

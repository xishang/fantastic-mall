package com.fantastic.mall.productservice.controller;

import com.fantastic.mall.productservice.dto.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xishang
 * @version 1.0
 * @date 2018/4/8
 */
@RestController
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private DiscoveryClient discoveryClient;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ResponseDTO getProductList() {
        ServiceInstance instance = discoveryClient.getLocalServiceInstance();
        logger.info("url = /products, host = {}, service_id = {}", instance.getHost(), instance.getServiceId());
        return new ResponseDTO(0, "success");
    }

}

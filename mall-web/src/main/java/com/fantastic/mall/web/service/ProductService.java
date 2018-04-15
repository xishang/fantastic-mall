package com.fantastic.mall.web.service;

import com.fantastic.mall.web.service.impl.ProductServiceImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author xishang
 * @version 1.0
 * @since 2018/4/10
 */
@FeignClient(value = "product-service", fallback = ProductServiceImpl.class)
public interface ProductService {

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    String getProductList();

}

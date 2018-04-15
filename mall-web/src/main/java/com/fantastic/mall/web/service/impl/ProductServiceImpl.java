package com.fantastic.mall.web.service.impl;

import com.fantastic.mall.web.service.ProductService;
import org.springframework.stereotype.Component;

/**
 * @author xishang
 * @version 1.0
 * @since 2018/4/10
 */
@Component
public class ProductServiceImpl implements ProductService {

    @Override
    public String getProductList() {
        return "Sorry, service is not available. -- by Feign";
    }

}

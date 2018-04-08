package com.fantastic.mall.productservice.dto;

import lombok.Data;

/**
 * @author xishang
 * @version 1.0
 * @date 2018/4/8
 */
@Data
public class ResponseDTO<T> {

    private Integer code;
    private String message;
    private T data;

    public ResponseDTO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}

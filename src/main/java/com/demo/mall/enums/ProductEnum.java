package com.demo.mall.enums;

import lombok.Getter;

/**
 * @author wucong
 * @date 2020/11/8 12:44
 * @description com.demo.mall.enums
 */
@Getter
public enum ProductEnum {

    ON_SALE(1),

    OFF_SALE(2),

    DELETE(3),

    ;

    private Integer code;

    ProductEnum(Integer code) {
        this.code = code;
    }
}

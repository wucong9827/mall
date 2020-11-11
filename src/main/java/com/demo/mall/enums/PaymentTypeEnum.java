package com.demo.mall.enums;

import lombok.Getter;

/**
 * @author wucong
 * @date 2020/11/10 15:04
 * @description com.demo.mall.enums
 */
@Getter
public enum PaymentTypeEnum {

    PAY_ONLINE(1),
    ;

    Integer code;

    PaymentTypeEnum(Integer code) {
        this.code = code;
    }
}

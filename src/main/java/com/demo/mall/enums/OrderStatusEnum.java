package com.demo.mall.enums;

import lombok.Getter;

/**
 * @author wucong
 * @date 2020/11/10 14:54
 * @description com.demo.mall.enums
 */
@Getter
public enum OrderStatusEnum {

    CANCELED(0, "已取消"),

    NO_PAY(10, "未付款"),

    PAID(20, "已付款"),

    SHIPPED(40, "已发货"),

    TRADE_SUCCESS(50, "交易成功"),

    TRADE_CLOSE(60, "交易关闭"),
    ;

    private Integer code;

    private String desc;

    OrderStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}

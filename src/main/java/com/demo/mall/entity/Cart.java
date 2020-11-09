package com.demo.mall.entity;

import lombok.Data;

/**
 * @author wucong
 * @date 2020/11/9 13:26
 * @description com.demo.mall.entity
 */
@Data
public class Cart {

    private Integer productId;

    private Integer quantity;

    private Boolean productSelected;

    public Cart() {
    }

    public Cart(Integer productId, Integer quantity, Boolean productSelected) {
        this.productId = productId;
        this.quantity = quantity;
        this.productSelected = productSelected;
    }
}

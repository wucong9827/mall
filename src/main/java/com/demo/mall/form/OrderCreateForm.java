package com.demo.mall.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wucong
 * @date 2020/11/10 15:15
 * @description com.demo.mall.form
 */
@Data
public class OrderCreateForm {

    @NotNull
    private Integer shippingId;
}

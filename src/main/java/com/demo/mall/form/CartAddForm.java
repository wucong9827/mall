package com.demo.mall.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wucong
 * @date 2020/11/8 15:40
 * @description com.demo.mall.form
 */
@Data
public class CartAddForm {

    @NotNull
    private Integer productId;

    private Boolean selected = true;
}

package com.demo.mall.form;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author wucong
 * @date 2020/11/8 15:42
 * @description com.demo.mall.form
 */
@Data
public class CartUpdateForm {

    @NotNull @Min(value = 0L)
    private Integer quantity;
    @NotNull
    private Boolean selected;
}

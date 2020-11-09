package com.demo.mall.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wucong
 * @date 2020/11/8 15:38
 * @description com.demo.mall.vo
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartVo {

    private List<CartProductVo> cartProductVos;

    private Boolean selectedAll;

    private BigDecimal cartTotalPrice;

    private Integer cartTotalQuantity;
}

package com.demo.mall.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wucong
 * @date 2020/11/10 15:19
 * @description com.demo.mall.vo
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemVo {

    private Long orderNo;

    private Integer productId;

    private String productName;

    private String productImage;

    private BigDecimal currentUnitPrice;

    private Integer quantity;

    private BigDecimal totalPrice;

    private Date createTime;
}

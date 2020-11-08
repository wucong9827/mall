package com.demo.mall.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wucong
 * @date 2020/11/7 16:36
 * @description com.demo.mall.vo
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductVo {

    private Integer id;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private String mainImage;

    private Integer status;

    private BigDecimal price;
}

package com.demo.mall.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wucong
 * @date 2020/11/7 17:06
 * @description com.demo.mall.vo
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDetailVo {

    private Integer id;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private String mainImage;

    private String subImages;

    private String detail;

    private BigDecimal price;

    private Integer stock;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}

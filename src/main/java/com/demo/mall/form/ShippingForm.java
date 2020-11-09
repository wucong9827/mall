package com.demo.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author wucong
 * @date 2020/11/9 20:06
 * @description com.demo.mall.form
 */
@Data
public class ShippingForm {

    @NotBlank
    private String receiverName;

    @NotBlank
    private String receiverPhone;

    @NotBlank
    private String receiverMobile;

    @NotBlank
    private String receiverProvince;

    @NotBlank
    private String receiverCity;

    @NotBlank
    private String receiverDistrict;

    @NotBlank
    private String receiverAddress;

    @NotBlank
    private String receiverZip;
}

package com.demo.mall.service.impl;

import com.demo.mall.MallApplicationTest;
import com.demo.mall.entity.Shipping;
import com.demo.mall.form.ShippingForm;
import com.demo.mall.service.IShippingService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author wucong
 * @date 2020/11/9 20:31
 * @description com.demo.mall.service.impl
 */
@Slf4j
public class ShippingServiceImplTest extends MallApplicationTest {

    @Autowired
    private IShippingService shippingService;

    private Gson gson = new Gson();

    @Test
    public void add() {
        ShippingForm form = new ShippingForm();
        form.setReceiverName("zmm");
        form.setReceiverAddress("慕课网");
        form.setReceiverCity("北京");
        form.setReceiverMobile("18812345678");
        form.setReceiverPhone("010123456xx");
        form.setReceiverProvince("重庆");
        form.setReceiverDistrict("北碚区");
        form.setReceiverZip("000000");

        log.info("result:[{}]", gson.toJson(shippingService.add(1, form)));
    }

    @Test
    public void delete() {
        log.info("result:[{}]", gson.toJson(shippingService.delete(1, 1)));
    }

    @Test
    public void update() {
        ShippingForm form = new ShippingForm();
        form.setReceiverName("zmm");
        form.setReceiverAddress("西南大学");
        form.setReceiverCity("重庆");
        form.setReceiverMobile("18812345678");
        form.setReceiverPhone("010123456xx");
        form.setReceiverProvince("重庆");
        form.setReceiverDistrict("北碚区");
        form.setReceiverZip("000000");
        log.info("result:[{}]", gson.toJson(shippingService.update(1, 3, form)));
    }

    @Test
    public void list() {
        log.info("result:[{}]", gson.toJson(shippingService.list(1, 1, 2)));
    }
}
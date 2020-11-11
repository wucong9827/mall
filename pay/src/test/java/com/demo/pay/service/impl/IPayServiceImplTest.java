package com.demo.pay.service.impl;

import com.demo.pay.service.IPayService;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author wucong
 * @date 2020/11/1 00:43
 * @description com.demo.pay.service.impl
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IPayServiceImplTest {

    @Autowired
    private IPayService iPayService;

    @Test
    public void create() {
       iPayService.create("1231432467", BigDecimal.valueOf(0.01), BestPayTypeEnum.WXPAY_NATIVE);
    }
}
package com.demo.mall.service.impl;

import com.demo.mall.MallApplicationTest;
import com.demo.mall.service.IOrderService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author wucong
 * @date 2020/11/10 15:29
 * @description com.demo.mall.service.impl
 */
@Slf4j
public class OrderServiceImplTest extends MallApplicationTest {

    @Autowired
    private IOrderService orderService;

    private Gson gson = new Gson();
    @Test
    public void create() {
        log.info("{}", gson.toJson(orderService.create(1, 2)));
    }

    @Test
    public void list() {
        log.info("{}", gson.toJson(orderService.list(1, 1, 2)));
    }

    @Test
    public void detail() {
    }

    @Test
    public void cancel() {
    }

    @Test
    public void paid() {
    }
}
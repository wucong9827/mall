package com.demo.mall.service.impl;

import com.demo.mall.MallApplicationTest;
import com.demo.mall.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wucong
 * @date 2020/11/8 11:11
 * @description com.demo.mall.service.impl
 */
@Slf4j
public class ProductServiceImplTest extends MallApplicationTest {

    @Autowired
    private IProductService productService;

    @Test
    public void list() {
        log.info("list : [{}]", productService.list(null,1,2));
    }

    @Test
    public void detail() {
        log.info("ID：[{}] - 商品详情：【{}】", 26, productService.detail(26));
    }
}
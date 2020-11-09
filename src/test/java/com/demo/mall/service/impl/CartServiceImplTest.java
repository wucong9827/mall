package com.demo.mall.service.impl;

import com.demo.mall.MallApplicationTest;
import com.demo.mall.form.CartAddForm;
import com.demo.mall.form.CartUpdateForm;
import com.demo.mall.service.ICartService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author wucong
 * @date 2020/11/9 13:35
 * @description com.demo.mall.service.impl
 */
@Slf4j
public class CartServiceImplTest extends MallApplicationTest {

    @Autowired
    private ICartService cartService;

    @Test
    public void add() {
        log.info("购物车add");
        CartAddForm cartAddForm = new CartAddForm();
        cartAddForm.setProductId(27);
        log.info("[{}]", cartService.add(1, cartAddForm));
    }

    @Test
    public void list() {
        log.info("[{}]", cartService.list(2));
    }

    @Test
    public void update() {
        CartUpdateForm cartUpdateForm = new CartUpdateForm();
        cartUpdateForm.setQuantity(1);
        cartUpdateForm.setSelected(false);
        log.info("[{}]", cartService.update(1, 26, cartUpdateForm));
    }

    @Test
    public void delete() {
        log.info("[{}]", cartService.delete(1, 26));
    }
}
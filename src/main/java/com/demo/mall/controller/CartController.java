package com.demo.mall.controller;

import com.demo.mall.consts.MallConst;
import com.demo.mall.entity.User;
import com.demo.mall.form.CartAddForm;
import com.demo.mall.form.CartUpdateForm;
import com.demo.mall.service.ICartService;
import com.demo.mall.vo.CartVo;
import com.demo.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author wucong
 * @date 2020/11/9 17:00
 * @description com.demo.mall.controller
 */
@RestController
@Slf4j
public class CartController {

    @Autowired
    private ICartService cartService;

    @GetMapping("/carts")
    public ResponseVo<CartVo> list(HttpSession session) {
        log.info("url:[/carts] - 查看购物车");
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.list(user.getId());
    }

    @PostMapping("/carts")
    public ResponseVo<CartVo> list(@Valid @RequestBody CartAddForm addForm,
                                   HttpSession session) {
        log.info("url:[/carts] - 添加购物车");
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.add(user.getId(), addForm);
    }

    @PutMapping("/carts/{productId}")
    public ResponseVo<CartVo> update(@PathVariable Integer productId,
                                     @Valid @RequestBody CartUpdateForm form,
                                     HttpSession session) {
        log.info("url:[/carts/{}] - 更新购物车", productId);
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.update(user.getId(), productId, form);
    }

    @DeleteMapping("/carts/{productId}")
    public ResponseVo<CartVo> delete(@PathVariable Integer productId,
                                     HttpSession session) {
        log.info("url:[/carts/{}] - 删除购物车", productId);
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.delete(user.getId(), productId);
    }

    @PutMapping("/carts/selectAll")
    public ResponseVo<CartVo> selectAll(HttpSession session) {
        log.info("url:[/carts/selectAll] - 全选购物车");
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.selectAll(user.getId());
    }

    @PutMapping("/carts/unSelectAll")
    public ResponseVo<CartVo> unSelectAll(HttpSession session) {
        log.info("url:[/carts/unSelectAll] - 不全选购物车");
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.unSelectAll(user.getId());
    }

    @GetMapping("/carts/products/sum")
    public ResponseVo<Integer> sum(HttpSession session) {
        log.info("url:[/carts/products/sum] - 购物车数量和");
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.sum(user.getId());
    }
}

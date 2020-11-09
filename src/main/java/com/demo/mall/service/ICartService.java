package com.demo.mall.service;

import com.demo.mall.entity.Cart;
import com.demo.mall.form.CartAddForm;
import com.demo.mall.form.CartUpdateForm;
import com.demo.mall.vo.CartVo;
import com.demo.mall.vo.ResponseVo;

import java.util.List;

/**
 * @author wucong
 * @date 2020/11/8 15:53
 * @description com.demo.mall.service
 */
public interface ICartService {

    /**
     * 添加商品进购物车
     * @param uid
     * @param cartAddForm
     * @return
     */
    ResponseVo<CartVo> add(Integer uid, CartAddForm cartAddForm);

    /**
     * 展示购物车商品
     * @param uid
     * @return
     */
    ResponseVo<CartVo> list(Integer uid);

    /**
     * 更新购物车信息
     * @param uid
     * @param productId
     * @param cartUpdateForm
     * @return
     */
    ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm);

    /**
     * 删除购物车内某个商品
     * @param uid
     * @param productId
     * @return
     */
    ResponseVo<CartVo> delete(Integer uid, Integer productId);

    /**
     * 全选
     * @param uid
     * @return
     */
    ResponseVo<CartVo> selectAll(Integer uid);

    /**
     * 不全选
     * @param uid
     * @return
     */
    ResponseVo<CartVo> unSelectAll(Integer uid);

    /**
     *商品数量和
     * @param uid
     * @return
     */
    ResponseVo<Integer> sum(Integer uid);

    /**
     * 返回列表
     * @param uid
     * @return
     */
    List<Cart> listForCart(Integer uid);
}

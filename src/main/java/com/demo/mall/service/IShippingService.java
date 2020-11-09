package com.demo.mall.service;

import com.demo.mall.form.ShippingForm;
import com.demo.mall.vo.ResponseVo;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * @author wucong
 * @date 2020/11/9 20:08
 * @description com.demo.mall.service
 */
public interface IShippingService {

    /**
     * 添加收货地址
     * @param uid
     * @param form
     * @return
     */
    ResponseVo<Map<String, Integer>> add(Integer uid, ShippingForm form);

    /**
     * 删除用户的收货地址
     * @param uid
     * @param shippingId
     * @return
     */
    ResponseVo delete(Integer uid, Integer shippingId);

    /**
     * 更新用户收货地址
     * @param uid
     * @param shippingId
     * @param form
     * @return
     */
    ResponseVo update(Integer uid, Integer shippingId, ShippingForm form);

    /**
     * 收货地址列表
     * @param uid
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize);
}

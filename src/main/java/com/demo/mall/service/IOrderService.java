package com.demo.mall.service;

import com.demo.mall.vo.OrderVo;
import com.demo.mall.vo.ResponseVo;
import com.github.pagehelper.PageInfo;

/**
 * @author wucong
 * @date 2020/11/10 15:06
 * @description 订单功能
 */
public interface IOrderService {

    /**
     * 创建订单
     * @param uid
     * @param shippingId
     * @return
     */
    ResponseVo<OrderVo> create(Integer uid, Integer shippingId);

    /**
     * 列出订单条目
     * @param uid
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize);

    /**
     * 订单详情
     * @param uid
     * @param orderNo
     * @return
     */
    ResponseVo<OrderVo> detail(Integer uid, Long orderNo);

    /**
     * 取消订单
     * @param uid
     * @param orderNo
     * @return
     */
    ResponseVo cancel(Integer uid, Long orderNo);

    /**
     * 支付
     * @param orderNo
     */
    void paid(Long orderNo);
}

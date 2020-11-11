package com.demo.pay.service;

import com.demo.pay.entry.PayInfo;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;

import java.math.BigDecimal;

/**
 * @author wucong
 * @date 2020/10/31 21:45
     * @description 支付服务
 */
public interface IPayService {

    /**
     * 创建/发起支付服务
     * @param orderId
     * @param amount
     * @param bestPayTypeEnum
     * @return
     */
    PayResponse create(String orderId, BigDecimal amount, BestPayTypeEnum bestPayTypeEnum);

    /**
     * 异步通知处理
     * @param notifyData
     */
    String asyncNotify(String notifyData);

    /**
     * 查询支付记录(通过订单号)
     * @param orderId
     * @return
     */
    PayInfo queryByOrderId(String orderId);
}

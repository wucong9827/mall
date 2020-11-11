package com.demo.pay.service.impl;

import com.demo.pay.dao.PayInfoMapper;
import com.demo.pay.entry.PayInfo;
import com.demo.pay.enums.PayPlatformEnum;
import com.demo.pay.service.IPayService;
import com.google.gson.Gson;
import com.lly835.bestpay.enums.BestPayPlatformEnum;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.enums.OrderStatusEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.BestPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author wucong
 * @date 2020/10/31 21:51
 * @description com.demo.pay.service.impl
 */
@Service
@Slf4j
public class IPayServiceImpl implements IPayService {

    private final static String QUEUE_PAY_NOTIFY = "payNotify";

    @Autowired
    private BestPayService bestPayService;

    @Autowired
    private PayInfoMapper payInfoMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public PayResponse create(String orderId, BigDecimal amount, BestPayTypeEnum bestPayTypeEnum) {
        // 1. 订单写入数据库
        PayInfo payInfo = new PayInfo(Long.parseLong(orderId),
                PayPlatformEnum.getByBestPayTypeEnum(bestPayTypeEnum).getCode(),
                OrderStatusEnum.NOTPAY.name(), amount);
        payInfoMapper.insertSelective(payInfo);

        PayRequest request = new PayRequest();
        request.setOrderName("4559066-支付测试");
        request.setOrderId(orderId);
        request.setOrderAmount(amount.doubleValue());
        request.setPayTypeEnum(bestPayTypeEnum);

        PayResponse response = bestPayService.pay(request);
        log.info("response:[{}]", response);
        return response;
    }

    /**
     * 异步通知处理
     * @param notify
     * @return
     */
    @Override
    public String asyncNotify(String notify) {
        // 1.签名校验
        PayResponse response  = bestPayService.asyncNotify(notify);
        log.info("notify: [{}]", response);

        // 2. 金额校验（数据库查订单）
        PayInfo payInfo = payInfoMapper.selectByOrderNo(Long.parseLong(response.getOrderId()));
        if (payInfo == null) {
            // 异常告警
            // 一般情况下无法发生，可以通过：钉钉，邮箱，短信进行告警
            throw new RuntimeException("通过orderNo查询到的结果是null");
        }
        // 订单状态不是"已经支付"
        if (!payInfo.getPlatformStatus().equals(OrderStatusEnum.SUCCESS.name())) {
            // Double类型比较大小，精度。1.00  1.0
            if (payInfo.getPayAmount().compareTo(BigDecimal.valueOf(response.getOrderAmount())) != 0) {
                // 告警
                throw new RuntimeException("异步通知中的金额和数据库里的不一致，[orderNo:" + response.getOrderId());
            }
            // 3. 修改订单支付状态
            payInfo.setPlatformStatus(OrderStatusEnum.SUCCESS.name());
            payInfo.setPlatformNumber(response.getOutTradeNo());
            payInfoMapper.updateByPrimaryKeySelective(payInfo);
        }

        // pay项目发送mq消息，mall服务负责接收
        amqpTemplate.convertAndSend(QUEUE_PAY_NOTIFY, new Gson().toJson(payInfo));

        if (response.getPayPlatformEnum() == BestPayPlatformEnum.WX) {
            // 4. 返回微信消息，停止发送通知
            return "<xml>\n" +
                    "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                    "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                    "</xml>";
        } else if (response.getPayPlatformEnum() == BestPayPlatformEnum.ALIPAY) {
            // 4. 返回阿里消息， 停止发送通知
            return "success";
        }
        throw new RuntimeException("异步通知中错误的支付平台");
    }

    @Override
    public PayInfo queryByOrderId(String orderId) {
        return payInfoMapper.selectByOrderNo(Long.parseLong(orderId));
    }
}


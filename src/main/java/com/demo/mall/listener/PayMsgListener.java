package com.demo.mall.listener;

import com.demo.mall.entity.PayInfo;
import com.demo.mall.service.IOrderService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wucong
 * @date 2020/11/11 20:24
 * @description 关于PayInfo，正确姿势：pay项目提供client.jar, mall项目引入jar包
 */
@Slf4j
@Component
@RabbitListener(queues = "payNotify")
public class PayMsgListener {

    @Autowired
    private IOrderService orderService;

    @RabbitHandler
    public void process(String msg) {
        log.info("接收消息 -> message:[{}]", msg);
        PayInfo payInfo = new Gson().fromJson(msg, PayInfo.class);
        if (payInfo.getPlatformStatus().equals("SUCCESS")) {
            // 修改订单状态
            orderService.paid(payInfo.getOrderNo());
        }
    }

}

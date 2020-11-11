package com.demo.pay.controller;

import com.demo.pay.entry.PayInfo;
import com.demo.pay.service.IPayService;
import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wucong
 * @date 2020/11/1 13:32
 * @description com.demo.pay.controller
 */
@RestController
@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Autowired
    private IPayService iPayService;
    @Autowired
    private WxPayConfig wxPayConfig;

    /**
     * 创建订单
     * @param orderId
     * @param amount
     * @return
     */
    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("amount") BigDecimal amount,
                               @RequestParam("payType") BestPayTypeEnum bestPayTypeEnum) {
        PayResponse payResponse = iPayService.create(orderId, amount, bestPayTypeEnum);
        Map<String, String> map = new HashMap<>();
        if (bestPayTypeEnum == BestPayTypeEnum.WXPAY_NATIVE) {
            map.put("codeUrl", payResponse.getCodeUrl());
            map.put("orderId", orderId);
            map.put("returnUrl", wxPayConfig.getReturnUrl());
            return new ModelAndView("createForWxNative", map);
        } else if (bestPayTypeEnum == BestPayTypeEnum.ALIPAY_PC) {
            map.put("body", payResponse.getBody());
            return new ModelAndView("createForAlipayPc", map);
        }
        throw new RuntimeException("不支持的支付类型");
    }

    /**
     * 接收微信返回的订单结果消息
     * @param notifyData
     */
    @PostMapping("/notify")
    public String asyncNotify(@RequestBody String notifyData) {
        return iPayService.asyncNotify(notifyData);
    }

    @GetMapping("/queryByOrderId")
    public PayInfo queryByOrderId(@RequestParam String orderId) {
        log.info("查询支付记录。。。");
        return iPayService.queryByOrderId(orderId);
    }

    @GetMapping("/finish")
    public ModelAndView finish() {
        return new ModelAndView("index");
    }

}

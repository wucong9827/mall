package com.demo.pay.enums;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import lombok.Getter;

/**
 * @author wucong
 * @date 2020/11/2 20:35
 * @description com.demo.pay.enums
 */
@Getter
public enum  PayPlatformEnum {

    ALIPAY(1),
    WX(2),
    ;

    private Integer code;

    PayPlatformEnum(Integer code) {
        this.code = code;
    }

    public static PayPlatformEnum getByBestPayTypeEnum(BestPayTypeEnum bestPayTypeEnum) {
        for (PayPlatformEnum payPlatformEnum : PayPlatformEnum.values()) {
            if (bestPayTypeEnum.getPlatform().name().equals(payPlatformEnum.name())) {
                return payPlatformEnum;
            }
        }
        throw new RuntimeException("无效的支付平台：" + bestPayTypeEnum.name());
    }
}

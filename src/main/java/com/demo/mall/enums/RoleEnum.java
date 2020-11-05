package com.demo.mall.enums;

import lombok.Getter;

/**
 * @author wucong
 * @date 2020/11/5 16:44
 * @description com.demo.mall.enums
 */

public enum RoleEnum {

    ADMIN(0),

    CUSTOMER(1),
    ;
    RoleEnum(Integer roleCode) {
        this.roleCode = roleCode;
    }
    @Getter
    private Integer roleCode;
}

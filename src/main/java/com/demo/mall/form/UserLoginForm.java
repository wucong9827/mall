package com.demo.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author wucong
 * @date 2020/11/5 23:40
 * @description com.demo.mall.form
 */
@Data
public class UserLoginForm {

    @NotNull
    private String username;

    @NotBlank
    private String password;
}

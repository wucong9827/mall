package com.demo.mall.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author wucong
 * @date 2020/11/5 20:14
 * @description com.demo.mall.form
 */
@Data
public class UserForm {

    @NotNull
    private String username;

    @Email
    private String email;

    @NotBlank
    private String password;
}

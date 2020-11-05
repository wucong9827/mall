package com.demo.mall.controller;

import com.demo.mall.entity.User;
import com.demo.mall.form.UserForm;
import com.demo.mall.service.IUserService;
import com.demo.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author wucong
 * @date 2020/11/5 19:11
 * @description com.demo.mall.controller
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 用户注册接口
     * @param userForm
     * @return
     */
    @PostMapping("/register")
    public ResponseVo registerUser(@Valid @RequestBody UserForm userForm) {
        log.info("用户注册：[{}]", userForm.getUsername());
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        return userService.register(user);
    }

}

package com.demo.mall.service.impl;

import com.demo.mall.MallApplicationTest;
import com.demo.mall.entity.User;
import com.demo.mall.enums.ResponseEnum;
import com.demo.mall.service.IUserService;
import com.demo.mall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * @author wucong
 * @date 2020/11/5 16:53
 * @description com.demo.mall.service.impl
 */
@Transactional
public class UserServiceImplTest extends MallApplicationTest {

    @Autowired
    private IUserService iUserService;

    @Test
    public void register() {
        User user = new User();
        user.setUsername("wucong");
        user.setEmail("wucong@qq.com");
        user.setPassword("wucong");
        iUserService.register(user);
    }

    @Test
    public void login() {
        ResponseVo responseVo = iUserService.login("admin", "admin");
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }
}
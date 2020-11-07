package com.demo.mall.service;

import com.demo.mall.entity.User;
import com.demo.mall.vo.ResponseVo;

/**
 * @author wucong
 * @date 2020/11/5 11:24
 * @description com.demo.mall.service
 */
public interface IUserService {

    /**
     * 用户注册
     * @param user
     * @return
     */
    ResponseVo register(User user);

    /**
     * 登陆功能
     * @param username
     * @param password
     * @return
     */
    ResponseVo<User> login(String username, String password);


}

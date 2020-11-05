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
     */
    ResponseVo register(User user);

}

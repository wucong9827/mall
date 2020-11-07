package com.demo.mall.service.impl;

import com.demo.mall.dao.UserMapper;
import com.demo.mall.entity.User;
import com.demo.mall.enums.ResponseEnum;
import com.demo.mall.enums.RoleEnum;
import com.demo.mall.service.IUserService;
import com.demo.mall.vo.ResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author wucong
 * @date 2020/11/5 11:24
 * @description com.demo.mall.service.impl
 */
@Service
public class UserServiceImpl implements IUserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseVo register(User user) {
        // 1.校验用户名
        int countByUsername = userMapper.countByUsername(user.getUsername());
        if (countByUsername > 0) {
            return ResponseVo.error(ResponseEnum.USERNAME_EXIST);
        }
        // 2.校验邮箱
        int countByEmail = userMapper.countByEmail(user.getEmail());
        if (countByEmail > 0) {
            return ResponseVo.error(ResponseEnum.EMAIL_EXIST);
        }
        user.setRole(RoleEnum.CUSTOMER.getRoleCode());
        // 3.对密码进行md5散列
        user.setPassword(DigestUtils.md5DigestAsHex(
                user.getPassword().getBytes(StandardCharsets.UTF_8)
        ));
        // 4.写入数据库
        int resultCount = userMapper.insertSelective(user);
        if (resultCount == 0) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo<User> login(String username, String password) {
        // 1.查询用户
        User user = userMapper.selectByUsername(username);
        if (Objects.isNull(user)) {
            // 用户未注册
            return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }
        // 2.校验密码,忽略大小写
        if (!user.getPassword().equalsIgnoreCase(DigestUtils.md5DigestAsHex(
                password.getBytes(StandardCharsets.UTF_8)))) {
            return ResponseVo.error(ResponseEnum.PASSWORD_ERROR);
        }
        user.setPassword(null);
        return ResponseVo.success(user);
    }
}
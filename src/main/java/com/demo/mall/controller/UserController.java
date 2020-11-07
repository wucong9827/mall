package com.demo.mall.controller;

import com.demo.mall.consts.MallConst;
import com.demo.mall.entity.User;
import com.demo.mall.enums.ResponseEnum;
import com.demo.mall.form.UserLoginForm;
import com.demo.mall.form.UserRegisterForm;
import com.demo.mall.service.IUserService;
import com.demo.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;

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
     * @param userRegisterForm
     * @return
     */
    @PostMapping("/register")
    public ResponseVo registerUser(@Valid @RequestBody UserRegisterForm userRegisterForm) {
        log.info("用户注册：[{}]", userRegisterForm.getUsername());
        User user = new User();
        BeanUtils.copyProperties(userRegisterForm, user);
        return userService.register(user);
    }

    /**
     * 用户登陆接口
     * TODO: 建议前端对密码进行非对称加密后传输，后端解密后验证
     * @return
     */
    @PostMapping("/login")
    public ResponseVo<User> login(@Valid @RequestBody UserLoginForm userLoginForm,
                            HttpSession session) {
        log.info("url:[/login] 用户登录：【{}】", userLoginForm.getUsername());
        ResponseVo responseVo = userService.login(userLoginForm.getUsername(),
                userLoginForm.getPassword());
        if (responseVo.getStatus().equals(ResponseEnum.SUCCESS.getCode())) {
            session.setAttribute(MallConst.CURRENT_USER, responseVo.getData());
            log.info("sessionId = {}", session.getId());
        }
        return responseVo;
    }

    /**
     * 返回个人中心信息
     * TODO： session是保存在内存中的，可以通过 Token + Redis 改进接口
     * @param session
     * @return
     */
    @GetMapping("/userInfo")
    public ResponseVo<User> userInfo(HttpSession session) {
        log.info("url:[/userInfo], sessionId= {}", session.getId());
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return ResponseVo.success(user);
    }

    @GetMapping("/logOut")
    public ResponseVo logOut(HttpSession session) {
        log.info("url:[/logout], sessionId = [{}]", session.getId());
        session.removeAttribute(MallConst.CURRENT_USER);
        return ResponseVo.success();
    }

}

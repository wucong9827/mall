package com.demo.mall.controller.Interceptor;

import com.demo.mall.consts.MallConst;
import com.demo.mall.entity.User;
import com.demo.mall.exception.UserLoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author wucong
 * @date 2020/11/6 16:19
 * @description com.demo.mall.controller.Interceptor
 */
@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("拦截器 preHandle..");
        User user = (User) request.getSession().getAttribute(MallConst.CURRENT_USER);
        if (Objects.isNull(user)) {
            log.info("该用户未登录");
            throw new UserLoginException();
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

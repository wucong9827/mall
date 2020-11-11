package com.demo.mall.controller;

import com.demo.mall.consts.MallConst;
import com.demo.mall.entity.User;
import com.demo.mall.form.OrderCreateForm;
import com.demo.mall.service.IOrderService;
import com.demo.mall.vo.OrderVo;
import com.demo.mall.vo.ResponseVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author wucong
 * @date 2020/11/10 15:06
 * @description com.demo.mall.controller
 */
@RestController
@Slf4j
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @PostMapping("/orders")
    public ResponseVo<OrderVo> create(@Valid @RequestBody OrderCreateForm form,
                                      HttpSession session) {
        log.info("url:[/orders] - 创建订单");
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.create(user.getId(), form.getShippingId());
    }

    @GetMapping("/orders")
    public ResponseVo<PageInfo> list(@RequestParam(required = false, value = "1") Integer pageNum,
                                     @RequestParam(required = false, value = "2") Integer pageSize,
                                     HttpSession session) {
        log.info("url:[/orders] - 订单列表");
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.list(user.getId(), pageNum, pageSize);
    }

    @GetMapping("/orders/{orderNo}")
    public ResponseVo<OrderVo> detail(@PathVariable Long orderNo,
                                      HttpSession session) {
        log.info("url:[/orders/{}] - 订单详情", orderNo);
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.detail(user.getId(), orderNo);
    }

    @PutMapping("/orders/{orderNo}")
    public ResponseVo cancel(@PathVariable Long orderNo,
                             HttpSession session) {
        log.info("url:[/orders/{}] -取消订单", orderNo);
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.cancel(user.getId(), orderNo);
    }
}

package com.demo.mall.service.impl;

import com.demo.mall.dao.ShippingMapper;
import com.demo.mall.entity.Shipping;
import com.demo.mall.enums.ResponseEnum;
import com.demo.mall.form.ShippingForm;
import com.demo.mall.service.IShippingService;
import com.demo.mall.vo.ResponseVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wucong
 * @date 2020/11/9 20:09
 * @description com.demo.mall.service.impl
 */
@Service
@Slf4j
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ResponseVo<Map<String, Integer>> add(Integer uid, ShippingForm form) {
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(form, shipping);
        shipping.setUserId(uid);
        int result = shippingMapper.insertSelective(shipping);
        if (result == 0) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        Map<String, Integer> data = new HashMap<>(1);
        data.put("shippingId", shipping.getId());
        return ResponseVo.success(data);
    }

    @Override
    public ResponseVo delete(Integer uid, Integer shippingId) {
        int result = shippingMapper.deleteByUidAndId(uid, shippingId);
        if (result == 0) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo update(Integer uid, Integer shippingId, ShippingForm form) {
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(form, shipping);
        shipping.setId(shippingId);
        shipping.setUserId(uid);
        int result = shippingMapper.updateByPrimaryKeySelective(shipping);
        if (result == 0) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUid(uid);
        PageInfo pageInfo = new PageInfo(shippingList);
        return ResponseVo.success(pageInfo);
    }
}

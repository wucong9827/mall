package com.demo.mall.dao;

import com.demo.mall.entity.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ShippingMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    int deleteByUidAndId(@Param("uid") Integer uid,
                         @Param("shippingId") Integer shippingId);

    List<Shipping> selectByUid(Integer uid);

    Shipping selectByUidAndShippingId(@Param("uid") Integer uid,
                                      @Param("shippingId") Integer shippingId);

    List<Shipping> selectByIdSet(@Param("shippingIdSet") Set<Integer> shippingIdSet);
}
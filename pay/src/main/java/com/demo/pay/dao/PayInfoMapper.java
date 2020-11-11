package com.demo.pay.dao;

import com.demo.pay.entry.PayInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wucong
 * @date 2020/11/3
 */
@Mapper
public interface PayInfoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(PayInfo record);

    int insertSelective(PayInfo record);

    PayInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PayInfo record);

    int updateByPrimaryKey(PayInfo record);

	PayInfo selectByOrderNo(Long orderNo);
}
package com.demo.mall.dao;

import com.demo.mall.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author wucong
 * @date 2020/10/29 19:29
 * @description com.demo.mall.dao
 */
public interface CategoryMapper {

    /**
     * 根据主键查找分类信息
     * @param id
     * @return
     */
    Category selectByPrimaryKey(@Param("id") Integer id);


}

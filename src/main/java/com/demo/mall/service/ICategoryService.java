package com.demo.mall.service;

import com.demo.mall.vo.CategoryVo;
import com.demo.mall.vo.ResponseVo;

import java.util.List;

/**
 * @author wucong
 * @date 2020/11/7 13:27
 * @description com.demo.mall.service
 */
public interface ICategoryService {

    /**
     * 获取所有的分类条目
     * @return
     */
    ResponseVo<List<CategoryVo>> selectAll();


}

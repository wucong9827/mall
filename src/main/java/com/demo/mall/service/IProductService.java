package com.demo.mall.service;

import com.demo.mall.vo.ProductDetailVo;
import com.demo.mall.vo.ResponseVo;
import com.github.pagehelper.PageInfo;

/**
 * @author wucong
 * @date 2020/11/7 16:05
 * @description com.demo.mall.service
 */
public interface IProductService {

    /**
     * 获取该类别下，对应的商品条目
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize);

    /**
     * 商品详情
     * @param productId
     * @return
     */
    ResponseVo<ProductDetailVo> detail(Integer productId);

}

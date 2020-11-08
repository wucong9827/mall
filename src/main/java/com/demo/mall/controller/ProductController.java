package com.demo.mall.controller;

import com.demo.mall.service.IProductService;
import com.demo.mall.vo.ProductDetailVo;
import com.demo.mall.vo.ResponseVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wucong
 * @date 2020/11/8 11:16
 * @description com.demo.mall.controller
 */
@RestController
@Slf4j
public class ProductController {

    @Autowired
    private IProductService productService;

    /**
     * 获取对应id下的所有商品条目
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/products")
    public ResponseVo<PageInfo> list(@RequestParam(required = false) Integer categoryId,
                                     @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                     @RequestParam(required = false, defaultValue = "2") Integer pageSize) {
        log.info("url:[/products] - 获取商品条目 - categoryId: [{}] ", categoryId);
        return productService.list(categoryId, pageNum, pageSize);
    }

    /**
     * 获取商品详细信息
     * @param productId
     * @return
     */
    @GetMapping("/product/{productId}")
    public ResponseVo<ProductDetailVo> detail(@PathVariable Integer productId) {
        log.info("url:[/product/{}] - 获取商品信息", productId);
        return productService.detail(productId);
    }
}

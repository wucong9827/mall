package com.demo.mall.service.impl;

import com.demo.mall.dao.ProductMapper;
import com.demo.mall.entity.Product;
import com.demo.mall.enums.ProductEnum;
import com.demo.mall.enums.ResponseEnum;
import com.demo.mall.service.ICategoryService;
import com.demo.mall.service.IProductService;
import com.demo.mall.vo.ProductDetailVo;
import com.demo.mall.vo.ProductVo;
import com.demo.mall.vo.ResponseVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wucong
 * @date 2020/11/7 16:05
 * @description com.demo.mall.service.impl
 */
@Service
@Slf4j
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize) {
        // 1.查找该categoryId下的种类
        Set<Integer> categoryIdSet = new HashSet<>();
        if (!Objects.isNull(categoryId)) {
            categoryService.findSubCategoryId(categoryId, categoryIdSet);
            categoryIdSet.add(categoryId);
        }
        // 分页
        PageHelper.startPage(pageNum, pageSize);
        // 2.查找对应种类的商品
        List<Product> products = productMapper.selectByCategoryIdSet(categoryIdSet);
        List<ProductVo> productVoList = products.stream()
                .map(e -> {
                    ProductVo productVo = new ProductVo();
                    BeanUtils.copyProperties(e, productVo);
                    return productVo;
                })
                .collect(Collectors.toList());
        // 用PageInfo 对结果 products进行包装
        PageInfo pageInfo = new PageInfo<>(products);
        // 设置需要传送的数据
        pageInfo.setList(productVoList);
        return ResponseVo.success(pageInfo);
    }

    @Override
    public ResponseVo<ProductDetailVo> detail(Integer productId) {
        Product product = productMapper.selectByPrimaryKey(productId);

        // 判断商品状态
        if (ProductEnum.OFF_SALE.getCode().equals(product.getStatus())
                || ProductEnum.DELETE.getCode().equals(product.getStatus())) {
            log.info("商品无效");
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }

        ProductDetailVo productDetailVo = new ProductDetailVo();
        BeanUtils.copyProperties(product, productDetailVo);
        // 库存敏感值，处理
        productDetailVo.setStock(product.getStock() > 100 ? 100 : product.getStock());
        return ResponseVo.success(productDetailVo);
    }
}

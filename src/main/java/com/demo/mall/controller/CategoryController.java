package com.demo.mall.controller;

import com.demo.mall.service.ICategoryService;
import com.demo.mall.vo.CategoryVo;
import com.demo.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wucong
 * @date 2020/11/7 13:56
 * @description com.demo.mall.controller
 */
@RestController
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/categories")
    public ResponseVo<List<CategoryVo>> selectAll() {
        return categoryService.selectAll();
    }

}

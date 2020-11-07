package com.demo.mall.service.impl;

import com.demo.mall.consts.MallConst;
import com.demo.mall.dao.CategoryMapper;
import com.demo.mall.entity.Category;
import com.demo.mall.service.ICategoryService;
import com.demo.mall.vo.CategoryVo;
import com.demo.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author wucong
 * @date 2020/11/7 13:28
 * @description com.demo.mall.service.impl
 */
@Service
@Slf4j
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResponseVo<List<CategoryVo>> selectAll() {
        // 1.查找所有的条目
        List<Category> categories = categoryMapper.selectAll();
        List<CategoryVo> categoryVos = new ArrayList<>();
        // 装配根类别
        log.info("父目录组装...");
        for (Category category : categories) {
            if (MallConst.ROOT_PARENT_ID.equals(category.getParentId())) {
                categoryVos.add(category2CategoryVo(category));
            }
        }
            // 父目录权重排序
        categoryVos.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());
        // 2.装配子类别
        log.info("子目录组装...");
        findSubCategory(categoryVos, categories);
        return ResponseVo.success(categoryVos);
    }

    /**
     * 组装子目录
     * @param categoryVos
     * @param categories
     */
    private void findSubCategory(List<CategoryVo> categoryVos, List<Category> categories) {
        for (CategoryVo categoryVo : categoryVos) {
            List<CategoryVo> subCategoryVoList = new ArrayList<>();
            for (Category category : categories) {
                //如果查到内容，设置subCategory, 继续往下查
                if (categoryVo.getId().equals(category.getParentId())) {
                    subCategoryVoList.add(category2CategoryVo(category));
                }
            }
            // 权重倒序，大到小
            subCategoryVoList.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());
            // 装进父目录
            categoryVo.setSubCategories(subCategoryVoList);
            // 递归查找下一级目录
            findSubCategory(subCategoryVoList, categories);
        }
    }

    private CategoryVo category2CategoryVo(Category category) {
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }
}

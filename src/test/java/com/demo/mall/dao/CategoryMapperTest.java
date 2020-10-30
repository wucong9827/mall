package com.demo.mall.dao;

import com.demo.mall.entity.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author wucong
 * @date 2020/10/29 19:34
 * @description com.demo.mall.dao
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryMapperTest {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void selectByPrimaryKey() {
        Category category = categoryMapper.selectByPrimaryKey(100001);
        System.out.println(category);
    }
}
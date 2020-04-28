package com.itlike.service.impl;

import com.itlike.dao.CategoryDao;
import com.itlike.domain.Category;
import com.itlike.service.CategoryService;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class CategoryServiceImpl implements CategoryService {
    /*注入dao,查询可以不加事务，修改需加事务*/
    @Setter
    private CategoryDao categoryDao;

    @Override
    public void save(Category category) {
        System.out.println(category+"--> into category service");
        /*调用dao*/
        categoryDao.save(category);
    }

    @Override
    public List<Category> getAllCategory() {
        System.out.println("get category list into service");
        /*调用dao*/
        List<Category> list = categoryDao.getAllCategory();
        return list;
    }

    @Override
    public Category getOneCategory(Integer cid) {
        Category category = categoryDao.getOneCategory(cid);
        return category;
    }

    @Override
    public void update(Category category) {
        categoryDao.update(category);
    }

    @Override
    public void delete(Category category) {
        categoryDao.delete(category);
    }
}

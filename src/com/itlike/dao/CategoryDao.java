package com.itlike.dao;

import com.itlike.domain.Category;

import java.util.List;

public interface CategoryDao {
    public void save(Category category);

    public List<Category> getAllCategory();

    public Category getOneCategory(Integer cid);

    public void update(Category category);

    public void delete(Category category);
}

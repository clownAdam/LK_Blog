package com.itlike.service;

import com.itlike.domain.Category;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CategoryService {
    public void save(Category category);

    public List<Category> getAllCategory();

    public Category getOneCategory(Integer cid);

    public void update(Category category);

    public void delete(Category category);
}

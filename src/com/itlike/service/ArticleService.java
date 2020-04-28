package com.itlike.service;

import com.itlike.domain.Article;
import com.itlike.domain.Category;
import com.itlike.domain.PageBean;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

public interface ArticleService {
    public List<Article> getAllArticle();

    PageBean getPageData(DetachedCriteria detachedCriteria, Integer currentPage, int pageSize);

    void delete(Article article);
    /*根据id获取分类*/
    List<Category> getCategory(Integer parentid);

    void save(Article article);

    Article getOneArticle(Integer article_id);

    void update(Article article);
}

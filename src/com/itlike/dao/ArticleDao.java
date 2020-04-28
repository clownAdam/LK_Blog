package com.itlike.dao;

import com.itlike.domain.Article;
import com.itlike.domain.Category;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

public interface ArticleDao {
    List<Article> getAllArticle();


    List<Article> getPageData(DetachedCriteria detachedCriteria, Integer index, Integer pageSize);

    Integer getTotalCount(DetachedCriteria detachedCriteria);

    void delete(Article article);

    List<Category> getCategory(Integer parentid);

    void save(Article article);

    Article getOneArticle(Integer article_id);

    void update(Article article);
}

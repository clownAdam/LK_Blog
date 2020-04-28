package com.itlike.service.impl;

import com.itlike.dao.ArticleDao;
import com.itlike.domain.Article;
import com.itlike.domain.Category;
import com.itlike.domain.PageBean;
import com.itlike.service.ArticleService;
import lombok.Setter;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Setter
    private ArticleDao articleDao;
    @Override
    public List<Article> getAllArticle() {
        System.out.println("article service");
        List<Article> articleList = articleDao.getAllArticle();
        return articleList;
    }

    @Override
    public PageBean getPageData(DetachedCriteria detachedCriteria, Integer currentPage, int pageSize) {
        PageBean<Article> pageBean = new PageBean<>();
        //查询数据库
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        Integer totalCount = articleDao.getTotalCount(detachedCriteria);
        pageBean.setTotalCount(totalCount);
        pageBean.setTotalPage(pageBean.getTotalPage());
        List<Article> list =  articleDao.getPageData(detachedCriteria,pageBean.getIndex(),pageBean.getPageSize());
        //计算
        pageBean.setList(list);
        System.out.println("into service -->  pageBean = " + pageBean);
        return pageBean;
    }

    @Override
    public void delete(Article article) {
        articleDao.delete(article);
    }

    @Override
    public List<Category> getCategory(Integer parentid) {
        List<Category> list = articleDao.getCategory(parentid);
        return list;
    }

    @Override
    public void save(Article article) {
        articleDao.save(article);
    }

    @Override
    public Article getOneArticle(Integer article_id) {
        Article resArticle = articleDao.getOneArticle(article_id);
        return resArticle;
    }

    @Override
    public void update(Article article) {
        articleDao.update(article);
    }
}

package com.itlike.web;

import com.itlike.domain.Article;
import com.itlike.domain.Category;
import com.itlike.domain.PageBean;
import com.itlike.service.ArticleService;
import com.opensymphony.xwork2.ActionSupport;
import lombok.Setter;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author clown
 */
public class WebAction extends ActionSupport {
    @Setter
    private ArticleService articleService;
    @Setter
    private Integer currPage = 1;
    @Setter
    private Integer parentid;
    @Setter
    private Integer cid;
    public void getPageList() throws IOException {
        System.out.println("web");
        /*离线查询条件*/
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Article.class);
        if(parentid !=null){
            List<Category> categorys = articleService.getCategory(parentid);
            //构建数组
            Object[] cidArrays = new Object[categorys.size()];
            for (int i = 0; i < categorys.size(); i++) {
                Category category = categorys.get(i);
                cidArrays[i] = category.getCid();
            }
            //设置条件
            System.out.println("Arrays.toString(cidArrays) = " + Arrays.toString(cidArrays));
            detachedCriteria.add(Restrictions.in("category.cid",cidArrays));
        }
        if(cid !=null){
            detachedCriteria.add(Restrictions.eq("category.cid",cid));
        }
        //调用业务层
        PageBean pageBean = articleService.getPageData(detachedCriteria, currPage, 5);
        System.out.println("pageBean = " + pageBean);
        /*json回显*/
        /*hibernate懒加载*/
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"handler","hibernateLazyInitializer"});
        JSONObject jsonObject = JSONObject.fromObject(pageBean, jsonConfig);
        System.out.println("jsonArray = " + jsonObject);
        System.out.println("===================================");
        /*响应给页面*/
        ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
        ServletActionContext.getResponse().getWriter().println(jsonObject.toString());
    }
    /*根据id获取指定方法*/
    @Setter
    private Integer id;
    public void getDetail() throws IOException {
        Article article = articleService.getOneArticle(id);
        /*json显示页面*/
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"handler","hibernateLazyInitializer"});
        JSONObject jsonObject = JSONObject.fromObject(article, jsonConfig);
        ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
        ServletActionContext.getResponse().getWriter().print(jsonObject.toString());
    }
}

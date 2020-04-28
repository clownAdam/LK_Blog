package com.itlike.web;

import com.itlike.domain.Article;
import com.itlike.domain.PageBean;
import com.itlike.service.ArticleService;
import com.opensymphony.xwork2.ActionSupport;
import lombok.Setter;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import java.io.IOException;

/**
 * @author clown
 */
public class WebAction extends ActionSupport {
    @Setter
    private ArticleService articleService;
    @Setter
    private Integer currPage = 1;
    public void getPageList() throws IOException {
        System.out.println("web");
        /*离线查询条件*/
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Article.class);

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
        ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
        ServletActionContext.getResponse().getWriter().println(jsonObject.toString());
    }
}

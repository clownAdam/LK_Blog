package com.itlike.web;

import com.itlike.domain.Article;
import com.itlike.domain.Category;
import com.itlike.domain.PageBean;
import com.itlike.service.ArticleService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ArticleAction extends ActionSupport implements ModelDriven<Article> {
    private Article article = new Article();

    @Override
    public Article getModel() {
        return article;
    }

    @Setter
    private ArticleService articleService;

    public String list() {
        System.out.println("article list action");
        List<Article> allArticle = articleService.getAllArticle();
        System.out.println(allArticle);
        /*存入值栈，转发jsp*/
        ActionContext.getContext().getValueStack().set("allArticle", allArticle);
        return "list";
    }

    /*获取分页数据*/
    @Setter
    private Integer currentPage = 1;
    @Setter
    private String content;

    public String pageList() {
        /*创建离线条件查询*/
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Article.class);
        System.out.println("currentPage = " + currentPage);
        //设置搜索查询条件
        System.out.println("content = " + content);
        if (content != null) {
            detachedCriteria.add(Restrictions.like("article_title", "%" + content + "%"));
        }

        //调用业务层进行查询
        PageBean pageBean = articleService.getPageData(detachedCriteria, currentPage, 5);
        System.out.println("\"=========================\" = " + "=========================");
        System.out.println(pageBean + "");
        //把查询的结构写入到值栈
        ActionContext.getContext().getValueStack().push(pageBean);
        return "list";
    }

    /*删除*/
    /*@Setter
    private Integer article_id;*/
    public String delete() {
        Article article2 = new Article();
        System.out.println("delete doing ......" + this.article.getArticle_id());
        //调用业务层
        article2.setArticle_id(this.article.getArticle_id());
        articleService.delete(article2);
        return "listres";
    }

    @Setter
    private Integer parentid;

    public String getCategory() throws IOException {
        System.out.println("parentid = " + parentid);
        //根据parentid查询分类
        List<Category> list = articleService.getCategory(parentid);
        System.out.println(list);
        //将查询的结果转成json
        JSONArray jsonArray = JSONArray.fromObject(list, new JsonConfig());
        System.out.println("jsonArray = " + jsonArray);
        /*响应给页面*/
        ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
        ServletActionContext.getResponse().getWriter().println(jsonArray.toString());
        return null;
    }

    /**
     * 添加文章
     *
     * @return 文件上传提供的三个属性
     */
    /*文件名称*/
    @Setter
    private String uploadFileName;
    /*上传文件*/
    @Setter
    private File upload;
    /*文件类型*/
    @Setter
    private String uploadContentType;

    public String add() throws IOException {
        System.out.println("add----article----");

        System.out.println("uploadFileName1 = " + uploadFileName);
        System.out.println("upload = " + upload);
        System.out.println("uploadContentType = " + uploadContentType);
        if (null != upload) {
            /*随机生成文件名称*/
            //1.获取文件扩展名
            int index = uploadFileName.lastIndexOf(".");
            String etx = uploadFileName.substring(index);
            System.out.println("etx = " + etx);
            /*随机生成文件名，拼接扩展名*/
            String uuid = UUID.randomUUID().toString();
            String uuidFileName = uuid.replace("-", "") + etx;
            System.out.println(uuidFileName);
            /*确定上传的路径*/
            String path = ServletActionContext.getServletContext().getRealPath("/upload");
            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }
            //拼接新的文件路径
            File desFile = new File(path + "/" + uuidFileName);
            /*文件上传*/
            FileUtils.copyFile(upload, desFile);
            /*设置图片路径及名称*/
            article.setArticle_pic(uuidFileName);
        }
        System.out.println("---------------------------------------");
        /*设置当前时间*/
        Date date = new Date();
        long dateTime = date.getTime();
        article.setArticle_time(dateTime);
        System.out.println(article);
        /*调用业务层，保存文章到数据库中*/
        articleService.save(article);
        return "listres";
    }

    public String edit() {
        System.out.println("edit--------------------------");
        System.out.println("article.getArticle_id() = " + article.getArticle_id());
        /*从数据库中获取文章*/
        Article resArticle = articleService.getOneArticle(article.getArticle_id());
        System.out.println("resArticle = " + resArticle);
        ActionContext.getContext().getValueStack().push(resArticle);
        /*跳转到编辑界面*/
        return "edit";
    }

    public String update() throws IOException {
        System.out.println("update------>\n" + article);
        /*判断是否更新图片，如果更新删除原来的图片*/
        if (null != upload) {
            /*确定上传的路径*/
            String path = ServletActionContext.getServletContext().getRealPath("/upload");
            /*删除原有文件*/
            String pic_image = article.getArticle_pic();
            if (pic_image != null || !"".equals(pic_image)) {
                File file = new File(path + pic_image);
                file.delete();
            }
            /*随机生成文件名称*/
            //1.获取文件扩展名
            int index = uploadFileName.lastIndexOf(".");
            String etx = uploadFileName.substring(index);
            System.out.println("etx = " + etx);
            /*随机生成文件名，拼接扩展名*/
            String uuid = UUID.randomUUID().toString();
            String uuidFileName = uuid.replace("-", "") + etx;
            System.out.println(uuidFileName);

            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }
            //拼接新的文件路径
            File desFile = new File(path + "/" + uuidFileName);
            /*文件上传*/
            FileUtils.copyFile(upload, desFile);
            /*设置图片路径及名称*/
            article.setArticle_pic(uuidFileName);
        }
        System.out.println("---------------------------------------");
        /*设置当前时间*/
        article.setArticle_time(System.currentTimeMillis());
        /*调用业务更新文章*/
        articleService.update(article);
        return "listres";
    }
}

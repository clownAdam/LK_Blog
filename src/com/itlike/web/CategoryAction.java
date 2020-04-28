package com.itlike.web;

import com.itlike.domain.Category;
import com.itlike.service.CategoryService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;

import java.io.IOException;
import java.util.List;

/**
 * @author clown
 */
public class CategoryAction extends ActionSupport implements ModelDriven<Category> {
    private Category category = new Category();
    @Override
    public Category getModel() {
        return category;
    }
    /**
    注入service*/
    @Setter
    private CategoryService categoryService;

    public String add(){
        System.out.println("CategoryAction com on");
        System.out.println(category);
        //调用业务层
        categoryService.save(category);
        return "listAction";
    }

    public String list(){
        System.out.println("list --> into action");
        /*调用业务层,查询所有分类*/
        List<Category> list = categoryService.getAllCategory();
        System.out.println("------------------------------");
        System.out.println(list);
        /*把数据存到值栈中*/
        ActionContext.getContext().getValueStack().set("categorylist",list);
        return "list";
    }
    public String updateUI() throws IOException {
        System.out.println("updateUI go to action");
        /*获取ajax传递的数据*/
        Integer cid = category.getCid();
        /*调用业务层*/
        Category oneCategory = categoryService.getOneCategory(cid);
        System.out.println("oneCategory = " + oneCategory);
        /*把数据传给页面-->也json数据格式响应给页面*/
        JSONArray jsonArray = JSONArray.fromObject(oneCategory, new JsonConfig());
        System.out.println("jsonArray = " + jsonArray);
        /*响应给页面*/
        ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
        ServletActionContext.getResponse().getWriter().println(jsonArray.toString());
        return null;
    }
    public String update(){
        System.out.println("update category------->");
        System.out.println(category);
        /*调用业务层*/
        categoryService.update(category);
        return "listAction";
    }
    public String delete(){
        System.out.println("delete category action by id --> into action");
        System.out.println(category);
        /*调用业务层*/
        categoryService.delete(category);
        return "listAction";
    }
}

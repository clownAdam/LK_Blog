package com.itlike.web;

import com.itlike.domain.User;
import com.itlike.service.LoginService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class LoginAction extends ActionSupport implements ModelDriven<User> {
    private User user = new User();
    @Override
    public User getModel() {
        return user;
    }
    //注入业务层
    private LoginService loginService;

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }
    /*login.action*/
    public String login(){
        System.out.println("demo login come on");
        System.out.println(user);
        //登录业务逻辑
        User resUser = loginService.login(user);
        if(resUser!=null){
            //保存用户信息
            ActionContext.getContext().getSession().put("curUser",resUser);
            //结果页跳转
            return SUCCESS;
        }else{
            //错误信息回显
            this.addActionError("username or password error,please try input!");
            //结果页跳转
            return LOGIN;
        }
    }
    /*退出*/
    public String logout(){
        System.out.println("来到了 log out");
        ActionContext.getContext().getSession().remove("curUser");
        return "logout";
    }

}

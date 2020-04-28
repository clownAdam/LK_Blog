package com.itlike.web;

import com.itlike.domain.User;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import org.apache.struts2.ServletActionContext;

/**
 * @author clown
 */
public class LoginInterceptor extends MethodFilterInterceptor {
    @Override
    protected String doIntercept(ActionInvocation actionInvocation) throws Exception {
        System.out.println("拦截器生效");
        /*判断用户是否登录*/
        User user = (User) ServletActionContext.getRequest().getSession().getAttribute("curUser");
        if (null == user) {
            /*没有登录*/
            ActionSupport action = (ActionSupport) actionInvocation.getAction();
            action.addActionError("你还没有登录,没有权限访问！");
            return "login";
        } else {
            /*队请求的方法进行放行*/
            return actionInvocation.invoke();
        }
    }
}

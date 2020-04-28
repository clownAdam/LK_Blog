package com.itlike.web.filter;

import org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author clown
 */
public class UmEditFilter extends StrutsPrepareAndExecuteFilter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        /*获取当前的请求*/
        HttpServletRequest request = (HttpServletRequest) req;
        /*获取请求地址*/
        String url = request.getRequestURI();
        String umedit = "js/umedit/jsp/controller.jsp";
        //如果是umedit请求，就不过滤;否则继续使用struts的过滤器
        if(url.contains(umedit)){
            /*放行:使用原生过滤器*/
            chain.doFilter(req,res);
        }else{
            /*放行:使用struts的过滤器*/
            super.doFilter(req,res, chain);
        }
    }
}

package com.system.sm.global;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 全局过滤器
 * 登录的限制，有些url只有登录才能访问，怎么才能限制。处理形形色色的url分布在各个模块里面
 * 这里就创建了过滤器，管理多个模块
 */
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    //拦截
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //需要先访问request和response,请求响应
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //判断现在的请求路径
        String path = request.getServletPath();

        //有点url可以不登陆也可以显示，比如登录界面只有登录才访问
        //比如有login关键词，不进行拦截，直接放过
        //会有大小写比如toLogin先全部转化为小写
        if(path.toLowerCase().indexOf("login")!=-1){
            //调用方法直接放过
            filterChain.doFilter(request,response);
        }else {
            //经过访问才能进行url
            HttpSession session =request.getSession();
            //从session里面把USER为键的对象拿出来
            Object obj = session.getAttribute("USER");
            //拿出来以后，判断

            if(obj!=null){
                //如果obj不为空，放过
                filterChain.doFilter(request,response);
            }else {
                //如果obj没有这个用户，强制去toLogin
                //过滤多个目录，有可能是根目录下面的，也有能是二级目录，用相对路径toLogin.do有问题
                //用绝对路径
                response.sendRedirect(request.getContextPath()+"/toLogin.do");
            }
        }


    }

    @Override
    public void destroy() {

    }
}

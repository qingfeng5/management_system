package com.system.sm.global;

import javax.servlet.*;
import java.io.IOException;


public class EncodingFilter implements Filter {
    //读取初始化参数的编码
    private String encoding ="UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //通过filterConfig获取初始化参数
        //用户没有进行初始化参数，那么初始化参数就为空，所有需要加个判断
        if (filterConfig.getInitParameter("ENCODING")!=null)
             encoding =filterConfig.getInitParameter("ENCODING");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //用户发送请求时，要通过过滤器
        //servletRequest给它请求编码等
        servletRequest.setCharacterEncoding(encoding);
        servletResponse.setCharacterEncoding(encoding);
        //这个过滤器不做拦截，不要加任何条件，直接放行。
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        encoding=null;
    }
}

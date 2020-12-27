package com.jsonyao.cs.filter;

import javax.servlet.*;
import java.io.IOException;

// XML不用添加@WebFilter注解
public class MyXmlServletFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("com.jsonyao.cs.filter.MyXmlServletFilter.init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("com.jsonyao.cs.filter.MyXmlServletFilter.doFilter");
    }

    @Override
    public void destroy() {
        System.out.println("com.jsonyao.cs.filter.MyXmlServletFilter.destroy");
    }
}

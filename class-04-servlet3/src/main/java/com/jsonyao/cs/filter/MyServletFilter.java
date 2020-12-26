package com.jsonyao.cs.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(value = "/myServletFilter")
public class MyServletFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("com.jsonyao.cs.filter.MyServletFilter.init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("com.jsonyao.cs.filter.MyServletFilter.doFilter");
    }

    @Override
    public void destroy() {
        System.out.println("com.jsonyao.cs.filter.MyServletFilter.destroy");
    }
}

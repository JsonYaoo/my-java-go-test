package com.jsonyao.cs.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

// SPI不用添加@WebFilter注解
public class MySpiServletFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("com.jsonyao.cs.filter.MySpiServletFilter.init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("com.jsonyao.cs.filter.MySpiServletFilter.doFilter");
    }

    @Override
    public void destroy() {
        System.out.println("com.jsonyao.cs.filter.MySpiServletFilter.destroy");
    }
}

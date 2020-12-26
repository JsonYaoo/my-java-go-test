package com.jsonyao.cs.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

// SPI不用添加@WebListener注解
public class MySpiServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("com.jsonyao.cs.listener.MySpiServletListener.contextInitialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("com.jsonyao.cs.listener.MySpiServletListener.contextDestroyed");
    }
}

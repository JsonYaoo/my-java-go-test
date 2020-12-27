package com.jsonyao.cs.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyJavaConfigServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("com.jsonyao.cs.listener.MyJavaConfigServletListener.contextInitialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("com.jsonyao.cs.listener.MyJavaConfigServletListener.contextDestroyed");
    }
}

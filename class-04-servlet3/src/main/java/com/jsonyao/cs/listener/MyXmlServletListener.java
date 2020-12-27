package com.jsonyao.cs.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

// XML不用添加@WebListener注解
public class MyXmlServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("com.jsonyao.cs.listener.MyXmlServletListener.contextInitialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("com.jsonyao.cs.listener.MyXmlServletListener.contextDestroyed");
    }
}

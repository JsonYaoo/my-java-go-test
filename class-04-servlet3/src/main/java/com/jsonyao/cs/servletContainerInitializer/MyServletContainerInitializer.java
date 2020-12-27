package com.jsonyao.cs.servletContainerInitializer;

import com.jsonyao.cs.filter.MySpiServletFilter;
import com.jsonyao.cs.handleTypes.MyServletHandler;
import com.jsonyao.cs.listener.MySpiServletListener;
import com.jsonyao.cs.servlet.MySpiServlet;
import org.springframework.util.CollectionUtils;

import javax.servlet.*;
import javax.servlet.annotation.HandlesTypes;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

// 感兴趣的类型: 处理业务逻辑, 凡是实现了MyServletHandler类(无论是抽象类还是实现类), 都被传入到Set<Class<?>>集合中
@HandlesTypes(value = {MyServletHandler.class})
public class MyServletContainerInitializer implements ServletContainerInitializer {

    /**
     * 启动Servlet: Tomcat.start()方法
     * @param handlesTypeSet         业务处理类集合
     * @param servletContext         应用上下文
     * @throws ServletException
     */
    @Override
    public void onStartup(Set<Class<?>> handlesTypeSet, ServletContext servletContext) throws ServletException {
        /**
         * 处理第一个参数
         */
        // 添加业务处理类实例集合
        List<MyServletHandler> myServletHandlerList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(handlesTypeSet)){
            for(Class handlesTypeClass : handlesTypeSet){
                if(!handlesTypeClass.isInterface() && !Modifier.isAbstract(handlesTypeClass.getModifiers())
                        && MyServletHandler.class.isAssignableFrom(handlesTypeClass)){
                    try {
                        myServletHandlerList.add((MyServletHandler) handlesTypeClass.newInstance());
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // 对所有业务处理类实例执行业务处理
        for(MyServletHandler myServletHandler : myServletHandlerList){
            myServletHandler.sayHello();
        }

        /**
         * 处理第二个参数
         */
        // 注册Servlet Listener
        servletContext.addListener(MySpiServletListener.class);

        // 注册Servlet
        ServletRegistration.Dynamic mySpiServlet = servletContext.addServlet("mySpiServlet", new MySpiServlet());
        mySpiServlet.addMapping("/mySpiServlet");

        // 注册Servlet Filter
        FilterRegistration.Dynamic mySpiServletFilter = servletContext.addFilter("mySpiServletFilter", MySpiServletFilter.class);
        mySpiServletFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/mySpiServlet/123");
    }

}

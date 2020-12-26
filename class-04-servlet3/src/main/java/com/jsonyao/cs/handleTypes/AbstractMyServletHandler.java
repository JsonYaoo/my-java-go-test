package com.jsonyao.cs.handleTypes;

import com.jsonyao.cs.handleTypes.MyServletHandler;

public abstract class AbstractMyServletHandler implements MyServletHandler {

    @Override
    public void sayHello() {
        System.out.println("com.jsonyao.cs.handleTypes.AbstractMyServletHandler");
    }

}

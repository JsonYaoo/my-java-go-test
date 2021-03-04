package com.jsonyao.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RequestMapping测试应用: 测试继承关系
 */
@RestController
//public class RequestMappingController3 extends AbstractControllerImpl2 implements IController{
public class RequestMappingController3 extends AbstractControllerImpl3 implements IController{

    @Override
    public void sayHi() {
        System.err.println("RequestMappingController3");
    }
}

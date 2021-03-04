package com.jsonyao.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * RequestMapping测试应用: 测试继承关系
 */
public class RequestMappingController4 implements BaseController2{

    /**
     * 23. 顶层接口类注解+顶层接口方法注解 + 非直属抽象类实现(方法有注解但没实现) + 非直属抽象类父类实现方法注解 + 二级接口注解
     * 测试结果: 顶层接口不生效, 顶层接口方法生效, 抽象类方法不生效, 抽象类父类方法不生效, 二级接口生效
     */
    @RequestMapping("sayHi24")
    @Override
    public void sayHi() {
        System.err.println("RequestMappingController4");
    }
}

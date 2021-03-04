package com.jsonyao.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;

//@RequestMapping("testa2")
public abstract class AbstractControllerImpl3 extends RequestMappingController4{

//    @RequestMapping("sayHi")
//    public void sayHi() {
//        System.err.println("AbstractControllerImpl");
//    }

    /**
     * 22. 顶层接口类注解+顶层接口方法注解 + 非直属抽象类实现(方法注解, 但继承了别的实现类) + 二级接口注解
     * 测试结果: 顶层接口类不生效, 顶层方法生效, 抽象方法生效, 二级接口生效
     */
//    @RequestMapping("sayHi2")
//    public void sayHi() {
//        System.err.println("AbstractControllerImpl");
//    }

    /**
     * 23. 顶层接口类注解+顶层接口方法注解 + 非直属抽象类实现(方法有注解但没实现) + 非直属抽象类父类实现方法注解 + 二级接口注解
     * 测试结果: 顶层接口不生效, 顶层接口方法生效, 抽象类方法不生效, 抽象类父类方法不生效, 二级接口生效
     */
    @RequestMapping("sayHi2")
    public abstract void sayHi();

}

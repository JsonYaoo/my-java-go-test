package com.jsonyao.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("testa2")
public abstract class AbstractControllerImpl2 {

//    @RequestMapping("sayHi")
//    public void sayHi() {
//        System.err.println("AbstractControllerImpl");
//    }

    /**
     * 19. 顶层接口类注解+顶层接口方法注解 + 非直属抽象类注解+非直属抽象方法注解(已实现) + 二级接口注解
     * 测试结果: 顶层接口类不生效, 顶层接口方法不生效, 抽象类不生效, 抽象方法生效, 二级接口生效
     */
    /**
     * 21. 顶层接口类注解+顶层接口方法注解 + 非直属抽象类实现(类和方法都无注解) + 二级接口注解
     * 测试结果: 顶层接口类不生效, 顶层方法生效, 二级接口生效
     */
//    @RequestMapping("sayHi2")
    public void sayHi() {
        System.err.println("AbstractControllerImpl");
    }

    /**
     * 20. 顶层接口类注解+顶层接口方法注解 + 非直属抽象类注解+非直属抽象方法注解(未实现) + 二级接口注解
     * 测试结果:
     */
//    @RequestMapping("sayHi2")
//    public abstract void sayHi();

}

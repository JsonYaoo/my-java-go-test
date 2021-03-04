package com.jsonyao.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RequestMapping测试应用: 测试继承关系
 */
@RestController
/**
 * 10. 顶层接口类注解+顶层接口方法注解 + 二级接口注解 + 底层类注解
 * 测试结果: 顶层接口类失效, 顶层接口方法生效, 二级接口失效, 底层接口生效
 */
/**
 * 11. 顶层接口类注解+顶层接口方法注解 + 抽象类注解 + 底层类注解
 * 测试结果: 顶层接口类不生效, 顶层接口方法生效, 抽象类不生效, 底层类生效
 */
@RequestMapping("testr")
public class RequestMappingController extends AbstractControllerImpl implements IController{

    /**
     * 15. 顶层接口类注解+顶层接口方法注解 + 抽象类注解+抽象方法注解(未实现) + 二级接口注解 + 底层方法注解
     * 测试结果: 顶层接口类不生效, 顶层接口方法不生效, 抽象类不生效, 抽象方法不生效, 二级接口类生效, 底层方法生效
     */
    /**
     * 16. 顶层接口类注解+顶层接口方法注解 + 抽象类注解+抽象方法注解(未实现) + 二级接口注解 + 底层类注解+底层方法注解
     * 测试结果: 顶层接口类不生效, 顶层接口方法不生效, 抽象类不生效, 抽象方法不生效, 二级接口类不生效, 底层类生效, 底层方法生效
     */
    /**
     * 17. 顶层接口类注解+顶层接口方法注解 + 抽象类注解+抽象方法注解(已实现) + 二级接口注解 + 底层类注解+底层方法注解
     * 测试结果: 顶层接口类不生效, 顶层接口方法不生效, 抽象类不生效, 抽象方法不生效, 二级接口类不生效, 底层类生效, 底层方法生效
     */
    /**
     * 18. 顶层接口类注解+顶层接口方法注解 + 抽象类注解+抽象方法注解(已实现) + 二级接口注解 + 底层类注解(出现更底层类但没类注解时)+底层方法注解
     * 测试结果: 顶层接口类不生效, 顶层接口方法不生效, 抽象类不生效, 抽象方法不生效, 二级接口类不生效, 底层类生效, 底层方法生效
     */
    @RequestMapping("sayHi3")
    @Override
    public void sayHi() {
        System.err.println("RequestMappingController");
    }
}

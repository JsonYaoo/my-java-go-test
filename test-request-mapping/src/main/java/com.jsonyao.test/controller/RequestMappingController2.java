package com.jsonyao.test.controller;

/**
 * 18. 顶层接口类注解+顶层接口方法注解 + 抽象类注解+抽象方法注解(已实现) + 二级接口注解 + 底层类注解(出现更底层类但没类注解时)+底层方法注解
 * 测试结果: 顶层接口类不生效, 顶层接口方法不生效, 抽象类不生效, 抽象方法不生效, 二级接口类不生效, 底层类生效, 底层方法生效
 */
public class RequestMappingController2 extends RequestMappingController{

}

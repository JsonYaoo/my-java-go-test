package com.jsonyao.cs.generic;

/**
 * 测试桥接方法-子类
 */
public class SubClass implements SuperClass<String>{

    @Override
    public void test(String t) {

    }

    // 编译错误, 不能重写test(Object s)方法
//    public void test(Object s) {
//
//    }

//    // 自动生成的桥接方法
//    public void test(Object s) {
//        test((String)s);
//    }
}

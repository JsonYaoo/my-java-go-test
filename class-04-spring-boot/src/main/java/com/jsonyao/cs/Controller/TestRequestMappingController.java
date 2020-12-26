package com.jsonyao.cs.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("testRequestMappingController")
public class TestRequestMappingController {

    // 测试成功, Controller上没有@RequestMapping也可以访问
    @RequestMapping("test01")
    public String test01(){
        return "Test 01";
    }

    // 测试成功, Controller上没有@RequestMapping, 方法@RequestMapping为空也可以访问
    @RequestMapping("")
    public String test02(){
        return "Test 02";
    }

    // 编译失败, 方法上@RequestMapping没写值与test02为空的冲突，报错
//    @RequestMapping
//    public String test03(){
//        return "Test 03";
//    }

    // 测试成功, value = "test04"效果跟直接声明效果一致
    @RequestMapping(value = "test04")
    public String test04(){
        return "Test 04";
    }

    // 编译报错, 除了value和path其他属性进行声明时, 必须声明路径参数
//    @RequestMapping(name = "test05")
//    public String test05(){
//        return "Test 05";
//    }

    // 测试成功, path = "test06"效果跟直接声明效果一致
    @RequestMapping(path = "test06")
    public String test06(){
        return "Test 06";
    }

    // 测试成功, 只有为POST请求且路径匹配时才进入方法
    @RequestMapping(method = RequestMethod.POST, value = "test07")
    public String test07(){
        return "Test 07";
    }

    // 测试成功, "name!=123"不能有空格符号, 只有name不为123时才进入方法
    @RequestMapping(params = "name!=123", value = "test08")
    public String test08(){
        return "Test 08";
    }

    // 测试成功, 只有为Header包含123时才进入方法
    @RequestMapping(headers = "My-Header = 123", value = "test09")
    public String test09(String name){
        return "Test 09";
    }

    // text/plain的意思是将文件设置为纯文本的形式，浏览器在获取到这种文件时并不会对其进行处理。
    // 测试成功, 只有为纯文本形式的请求才进入方法
    @RequestMapping(consumes = "text/plain", value = "test10")
    public String test10(String name){
        return "Test 10";
    }

    // 测试成功, 只有输出结果为纯文本形式才进入方法, 返回值为空时能进入方法
    @RequestMapping(produces = "text/plain", value = "test11")
    public void test11(String name){
//        return "Test 11";
        System.out.println("Test 11");
    }

}

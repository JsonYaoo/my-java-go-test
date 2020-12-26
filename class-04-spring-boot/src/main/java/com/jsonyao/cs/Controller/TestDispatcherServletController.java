package com.jsonyao.cs.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

//@Controller
@RestController
@RequestMapping("/testDispatcherServletController")
public class TestDispatcherServletController {

    /**
     * 无参无页面调用链测试
     * @return
     */
    @RequestMapping("/testRestController")
    public String testRestController(){
        System.out.println("Test RestController~~~");
        return "Test RestController~~~";
    }

    /**
     * 请求参数无页面调用链测试
     * @param name
     * @param age
     * @return
     */
    @RequestMapping("/testRequestParams")
    public ModelAndView testRequestParams(String name, Integer age){
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        return null;
    }

    /**
     * 请求参数无页面调用链测试
     * @param name
     * @return
     */
    @RequestMapping("/testRequestParam")
    public ModelAndView testRequestParam(@RequestParam String name){
        System.out.println("Name: " + name);
        return null;
    }

    /**
     * URL参数无页面调用链测试
     * @param name
     * @return
     */
    @GetMapping("/testUrlParam/{name}")
    public ModelAndView testUrlParam(@PathVariable String name){
        System.out.println("Name: " + name);
        return null;
    }
}

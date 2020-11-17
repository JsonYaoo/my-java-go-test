package com.jsonyao.cs.generic;

/**
 * 泛型测试类-生物
 */
public class Creature {

    public Creature() {

    }

    public void say(){
        System.out.println("我是生物...");
    }

    @Override
    public String toString() {
        say();
        return "";
    }
}

package com.jsonyao.cs.generic;

/**
 * 泛型类
 */
public class MyGeneric<T> {

    private T genericCode;

    // 静态方法-不含泛型
    public static void main(String[] args) {

    }

    // 静态泛型方法-含泛型
    public static <E> E test(E args) {
        return args;
    }

    // 构造方法-使用类的泛型
    public MyGeneric(T genericCode) {
        this.genericCode = genericCode;
    }

    // 普通方法-使用类的泛型
    public T getGenericCode() {
        return genericCode;
    }

    // 普通方法-使用类的泛型
    public void setGenericCode(T genericCode) {
        this.genericCode = genericCode;
    }
}

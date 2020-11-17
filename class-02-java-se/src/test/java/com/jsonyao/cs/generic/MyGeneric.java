package com.jsonyao.cs.generic;

/**
 * 泛型类
 */
public class MyGeneric<T> {

    private T genericCode;

    public MyGeneric(T genericCode) {
        this.genericCode = genericCode;
    }

    public T getGenericCode() {
        return genericCode;
    }

    public void setGenericCode(T genericCode) {
        this.genericCode = genericCode;
    }
}

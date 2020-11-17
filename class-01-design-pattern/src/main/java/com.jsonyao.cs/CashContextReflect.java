package com.jsonyao.cs;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CashContextReflect {
     Class<?> clazz = null;
     Object obj = null;
     public CashContextReflect(String className, Class[] paramsType, Object[] parmas){
         try {
             clazz = Class.forName(className);
             Constructor con = clazz.getConstructor(paramsType);
             obj = con.newInstance(parmas);
         } catch (InstantiationException | IllegalAccessException e) {
             e.printStackTrace();
         } catch (ClassNotFoundException e) {
             e.printStackTrace();
         } catch (IllegalArgumentException e) {
             e.printStackTrace();
         } catch (InvocationTargetException e) {
             e.printStackTrace();
         } catch (NoSuchMethodException e) {
             e.printStackTrace();
         } catch (SecurityException e) {
             e.printStackTrace();
         }
         
     }
     
     public double getResult(double money){
         
         return ((CashSuper)obj).acceptCash(money);
     }
 }
package com.jsonyao.cs.reflect;

import java.lang.reflect.Field;

/**
 * 反射测试类
 */
public class Client {

    public static void main(String[] args)  {
        /**
         * 1、创建Class对象
         */
//        // 1、类.Class
//        Class personClazz = Person.class;
//        System.out.println(personClazz);
//
//        // 2、实例.getClass()
//        Person person = new Person("张三", 24);
//        System.out.println(person.getClass());
//        System.out.println(person.getClass().equals(personClazz));// true
//
//        // 3、Class.forName("类的全路径")
//        Class aClass = null;
//        try {
//            aClass = Class.forName("com.jsonyao.cs.reflect.Person");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        System.out.println(aClass);
//        System.out.println(aClass.equals(personClazz));// true

        /**
         * 2、通过Class创建对象
         */
//        // 正常实例化
//        Person person = new Person("李四", 25);
//        System.out.println(person);
//
//        // 反射实例化
//        // 1、先获取Class对象
//        Class personClazz = Person.class;
//        try {
//            // 2、获得构造方法
//            Constructor<Person> constructor = personClazz.getConstructor(String.class, int.class);
//
//            // 3、创建对象 => 调用构造方法创建对象 == new Person("李四", 25);
//            Person person1 = constructor.newInstance("李四", 25);
//
//            System.out.println(person1);
//            System.out.println(person1.equals(person));// 两次实例化不是同一个
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }

//        /**
//         * 3、通过反射获取属性值
//         */
//        Class personClazz = Person.class;
//
//        // Field.java
//        Field[] fields = personClazz.getFields();
//        Field field = fields[0];
//        try {
//            String name = (String) field.get(new Person("李四", 25));
//            System.out.println(name);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        // Constructor.java
//        try {
//            Constructor<Person> constructor = personClazz.getConstructor(String.class, int.class);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//
//        // Method.java
//        Method[] methods = personClazz.getMethods();
//        Method method = methods[0];
//
//        // Package.java
//        Package aPackage = personClazz.getPackage();
//        System.out.println();
//
//        // 设置私有属性
//        Person person = new Person("王五", 25);
//        Field[] declaredFields = personClazz.getDeclaredFields();// 获取本类全部的属性
//        Field declaredField = declaredFields[1];
//        try {
//            declaredField.setAccessible(true);// private类型需要开通访问权限
//            declaredField.set(person, 27);// 不开通权限不能设置
//            System.out.println(declaredField.get(person));// 不开通访问权限连获取都获取不了
////            declaredField.set(person, "27");// 数据类型必须强一致
//            System.out.println(person);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        Person womanPerson = new WomanPerson("小小", 26);
//        Class womanPersonClazz = womanPerson.getClass();
//        Field[] fields1 = womanPersonClazz.getFields();// 能够获取本类及其基类的public属性, 但不能获取非public属性(包括本身的非public)
//        Field[] declaredFields1 = womanPersonClazz.getDeclaredFields();// 能够获取本类所有属性, 但不能获取基类属性
//        System.out.println(fields1.equals(declaredFields1));// false

        /**
         * 4、创建一个反射工具类-完成对象属性复制
         */
        Person person = new Person("李四", 24);
        Person person1 = new Person();
        cloneObjectFields(person, person1);
        System.out.println(person);
        System.out.println();
        System.out.println(person1);
    }

    public static void cloneObjectFields(Object source, Object target){
        if(source == null || target == null){
            return;
        }

        // 1、获取Class对象
        Class sourceClass = source.getClass();
        Class targetClass = target.getClass();

        // 2、 获取本类所有声明属性
        Field[] sourceFields = sourceClass.getDeclaredFields();
        Field[] targetFields = targetClass.getDeclaredFields();

        // 3、遍历所有属性
        for(Field sourceField : sourceFields){
            for(Field targetField : targetFields){
                if(targetField.getName().equals(sourceField.getName())){
                    // 4、开启访问权限
                    sourceField.setAccessible(true);
                    targetField.setAccessible(true);

                    try {
                        // 5、属性复制
                        targetField.set(target, sourceField.get(source));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        }
    }

}

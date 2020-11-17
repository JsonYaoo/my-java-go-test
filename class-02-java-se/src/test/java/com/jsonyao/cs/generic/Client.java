package com.jsonyao.cs.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * 泛型测试类
 */
public class Client {

    public static void main(String[] args) {
        /**
         * 1、测试泛型使用
         *      A. 结果:
         *          a. 由于list集合存放了String和Integer类型, 在强制转换时发生了异常
         *      B. 因此, 引入了泛型概念 => 类型参数化
         */
//        List testList1 = new ArrayList();
//        testList1.add("aaa");
//        testList1.add(123);
//
//        for(int i = 0; i < testList1.size(); i++){// java.lang.ClassCastException: java.lang.Integer cannot be cast to java.lang.String
//            System.out.println((String) testList1.get(i));
//        }

//        System.out.println();

        /**
         * 2、测试泛型擦除
         *      A. 结果:
         *          a. 可见, 程序在编译之后会采取去泛型化的措施, 也即是说Java中的泛型在编译期间有效, 在运行期间无效
         *              => 泛型类型在逻辑上看是多个不同的类型, 但是实际上都是相同的类型
         *      B. 因此, List<String>和List<Integer>编译后经过泛型擦除, 两者的Class对象都是List, 而Class文件只有一份, 所有为true
         */
//        List<String> stringTestList = new ArrayList<>();
//        List<Integer> integerTestList = new ArrayList<>();
//
//        System.out.println(stringTestList.getClass().equals(integerTestList.getClass()));// true

//        System.out.println();

        /**
         * 3、测试泛型类
         *      A. 结果:
         *          a. 泛型类的构造方法如果制定了类的泛型, 那么必修与类泛型保持一致
         *          b. 泛型的类型参数只能是对象的类型, 不能使简单类型
         *          c. 如果泛型类使用时不指定泛型类型, 则可以使用任意的类型
         *              => 所以使用泛型类是只有指定了泛型类型, 其泛型才起到该有的作用
         *          d. 不能对确切的泛型类型使用instanceof操作, 但可以对没有确切泛型类型的类使用instanceof操作
         *      B. 因此, 使用泛型类时, 当指定泛型, 整个类的泛型都是同一类型; 当没有指定泛型, 整个类可以为任意类型
         */
////        MyGeneric<Integer> testGeneric1 = new MyGeneric<>("aaa");// 编译错误, 泛型类的构造方法如果制定了类的泛型, 那么必修与类泛型保持一致
////        MyGeneric<int> testGeneric2 = new MyGeneric<>(123);// 编译错误, 泛型的类型参数只能是对象的类型, 不能使简单类型
//        MyGeneric<Integer> testGeneric3 = new MyGeneric<>(123);// 编译通过
//        System.out.println(testGeneric3.getGenericCode());
//
//        MyGeneric testGeneric4 = new MyGeneric("aaa");// 编译通过, 如果泛型类使用时不指定泛型类型, 则可以使用任意的类型
//        MyGeneric testGeneric5 = new MyGeneric(123);// 编译通过, 如果泛型类使用时不指定泛型类型, 则可以使用任意的类型
//        MyGeneric testGeneric6 = new MyGeneric(false);// 编译通过, 如果泛型类使用时不指定泛型类型, 则可以使用任意的类型
//
////        System.out.println(testGeneric4 instanceof MyGeneric<Integer>);// 编译错误, 不能对确切的泛型类型使用instanceof操作
//        System.out.println(testGeneric4 instanceof MyGeneric);// 编译通过, 可以对没有确切泛型类型的类使用instanceof操作
//
//        System.out.println();

        /**
         * 4、测试泛型接口 & 泛型实现类
         *      A. 结果:
         *          a. 当实现类实现泛型接口时
         *              a.1. 如果没有指定接口泛型类型, 则实现类方法默认为Object类型 或者 实现了声明为同样的泛型类型T
         *              a.2. 如果指定了接口泛型类型, 则实现类方法默认为该泛型类型
         *      B. 因此, 实现泛型接口时, 实现类实现的泛型接口:
         *          a. 实现类明确类型 => 实现类不用指定泛型
         *          b. 实现类不明确类型:
         *              b.1. 使用Object类型 => 实现类和接口都不使用泛型
         *              b.2. 不使用Object类型 => 实现类和接口保持相同的泛型型
         */
//        Generator myGenerator3 = new MyGenerator();// 编译通过, 实现类不指定泛型类型时, 引用也不需要使用泛型
//        Generator<Integer> myGenerator2 = new MyGenerator();// 编译错误, 当实现类指定了泛型接口类型, 则泛型接口引用必须为该类型或者不使用泛型
//        Generator<String> myGenerator1 = new MyGenerator<>();// 编译通过, 指定了泛型接口类型, 则实现类也必须为该类新
//        System.out.println();

        /**
         * 5、测试泛型方法
         */




        /**
         * 4、类型通配符
         */
        // 1、测试没有泛型是否可用
        List<String> nameList = new ArrayList<>();
        nameList.add("123");
        test1(nameList);// 可用

        // 2、测试Object泛型是否可用
//        test2(nameList);// 不可用

        // 3、测试通配符是否可用
        test3(nameList);// 可用

        // 4、测试泛型的上限 <? extends 类型>
        Creature creature = new Creature();
        List<Creature> creatureList = new ArrayList<>();

        Animal animal = new Animal();
        List<Animal> animalList = new ArrayList<>();

        Dog dog = new Dog();
        List<Dog> dogList = new ArrayList<>();

        creatureList.add(creature);// 可用
        creatureList.add(animal);// 可用
        creatureList.add(dog);// 可用

//        animalList.add(creature);// 不可用
        animalList.add(animal);// 可用
        animalList.add(dog);// 可用

//        dogList.add(creature);// 不可用
//        dogList.add(animal);// 不可用
        dogList.add(dog);// 可用

        testUp(dogList);// 可用
        testUp(animalList);// 可用
//        testUp(creatureList);// 不可用

        /**
         * 4.1、测试泛型上限赋值 -> 代表容器至多为Animal类型
         */
        List<? extends Animal> upList = new ArrayList<>();
//        upList.add(dog);// 不可用
//        upList.add(animal);// 不可用
//        upList.add(creature);// 不可用

        upList = dogList;// 可用
        upList = animalList;// 可用
//        upList = creatureList;// 不可用

        /**
         * 4.2、测试泛型获取对象
         */
//        Dog dog1 = upList.get(0);// 不可用
        Animal animal1 = upList.get(0);// 可用
        Creature creature1 = upList.get(0);// 可用

        /**
         * 5、测试泛型的上限 <? super 类型>
         */
        testDown(creatureList);// 可用
        testDown(animalList);// 可用
//        testDown(dog);// 不可用

        /**
         * 5.1、测试泛型下限赋值 -> 代表容器装的是至少为Animal的类型
         */
        List<? super Animal> downList = new ArrayList<>();
        downList.add(dog);// 可用
        downList.add(animal);// 可用
//        downList.add(creature);// 不可用

//        downList = dogList;// 不可用
        downList = animalList;// 可用
        downList = creatureList;// 可用

        /**
         * 5.2、测试泛型获取对象 -> 由于
         */
//        Dog dog2 = downList.get(0);// 不可用
//        Animal animal2 = downList.get(0);// 不可用
//        Creature creature2 = downList.get(0);// 不可用
    }

    /**
     * 测试没有泛型是否可用
     * @param list
     */
    public static void test1(List list){// 非泛型依然可用
        System.out.println(list.get(0));
        return;
    }

    /**
     * 测试Object泛型是否可用
     * @param list
     */
    public static void test2(List<Object> list){// Object泛型不可用
        System.out.println(list.get(0));
        return;
    }

    /**
     * 测试通配符是否可用
     * @param list
     */
    public static void test3(List<?> list){// 可用
        System.out.println(list.get(0));
        return;
    }

    public static void testUp(List<? extends Animal> list){
        list.get(0).say();
    }

    public static void testDown(List<? super Animal> list){
        list.get(0).toString();
    }
}

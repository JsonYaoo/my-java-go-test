package com.jsonyao.cs.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * 泛型测试类
 */
public class Client {

    public static void main(String[] args) {



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

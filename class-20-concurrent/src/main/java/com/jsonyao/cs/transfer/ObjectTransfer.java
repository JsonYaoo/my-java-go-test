package com.jsonyao.cs.transfer;

import java.lang.ref.*;

/**
 * 测试对象实参传递
 *
 * @author yaocs2
 * @since 2021-05-28
 */
public class ObjectTransfer {

    /**
     * 测试结果:
     *   1) 对象的参数传递, 传递的是对象引用的副本。
     *   2) 在引用副本所引用的对象, 没有提供操作对象成员变量的方法时, 该引用副本不会修改原引用的对象, 比如String。
     *   3) 在引用副本所引用的对象, 有提供操作对象成员变量的方法时, 该引用副本会修改原引用的对象, 比如StringBuilder.append(i);
     *   4) 在引用副本被更改后, 不会影响到原引用的值。
     *   5) 对象变量的赋值, 为引用赋值, 即新变量和老变量具有相同的引用。
     * 此外:
     *   1) 基本类型的变量为值传递, 如int i = 1;
     *   2) boolean、byte、short、char、int、float以及对应的引用类型在栈上占4个字节, long、double在栈上占8字节, 也就是对于每个方法来说, 栈上的空间在编译时已经确定了的。
     *      而new Object()只是在堆中分配空间, 再由栈引用指向堆中的对象。
     *   3) int[] arr = new int[2], 此时栈中的arr引用占4个字节, 指向堆中开辟的2个连续的int 4个字节空间的首地址,
     *      int[][] arr2 = new int[2][4], 此时栈中的arr2引用还是占4个字节, 指向堆中开辟的2个连续的int[] 4个字节的空间的首地址, 而每个int[]又指向堆中开辟的4个连续的int 4个字节空间的首地址。
     *      int[][] arr3 = new int[2][], 此时栈中的arr2引用还是占4个字节, 指向堆中开辟的2个连续的int[] 4个字节的空间的首地址, 而每个int[]指向空的首地址, 即为null
     *   4) String str = new String("hello"), 此时栈中的str引用指向堆区中的String对象的首地址, 而String对象里有char[] chars、int startIndex、int length属性,
     *      其中chars引用指向‘h’ ‘e’ ‘l’ ‘l’ ‘o’字符数组的首地址。
     *   5) Java只有引用, 没有指针, 但引用又近似于指针, 所以容易混淆: 因为指针应该是可以自由操作地址指向的, 而Java引用虽然像指针那样知道指向的地址, 但并不能操纵它, 所以并不是指针。
     *      而Java的引用和C++的引用一样, 引用强调一旦引用指向了对象, 则不能再被更改, 对象变了则引用的东西也跟着变了, 强调的是, 对象的不变性(即一定要"小明干活"),
     *      类似于对象的别名, 比如:对象(员工)"小明"改名为了"小强", 此时引用(上司)还是知道那个对象还是"小强"; 指针强调的是指向的地址是自由的, 即关系自由性(谁干活不重要, 但一定要有人干),
     *      比如指针(上司)与地址"小明"交接内容, 但第二天地址"小明"辞职了, 这时指针(上司)可以把活分配给新的地址"小强"。(这里讨论引用和指针, 不讨论引用的传递和指针的传递)。
     *      => 所以Java只有引用, 没有指针。
     *   5) 此外Java的Reference对象有4个实现: 即Java有4种引用类型 => 下面的该对象意思都是为引用所引用的对象, 并不是引用本身
     *      a. 强引用(没有引用队列): FinalReference, 被强引用的对象永远也不会被垃圾回收, 即使是将发生OOM也不能。
     *          强引用本身的特点: 可直接访问目标对象、由于上面的特性可能会发生内存泄露问题(没用的对象一直没被回收)。
     *          强引用作用: 直接访问目标对象、做业务操作。
     *      b. 软引用(加入引用队列只是为了删除引用本身): SoftReference, 只被软引用引用的对象不会被JVM立即回收, JVM会根据使用情况判断,
     *                                              只有在堆内存临近阈值时, JVM才会去回收这些对象。
     *          软引用本身的特点: 相比强引用, 软引用作用到对象上后不会妨碍该对象的垃圾回收、get()会返回该对象的强引用(回收后会返回null)、
     *                          该对象被垃圾回收JVM会把软引用加入引用队列(用于删除软引用), 即在出栈时判断: 如果该软引用所引用的对象已经被删除了, 那么就会删除该软引用
     *          软引用作用: 用于实现对内存敏感的高速缓存, 如果对直接访问真实数据的内存比较敏感, 那么可以考虑拷贝一份该数据作为缓存, 并用软引用指向这些缓存,
     *                    JVM会在内存不足时才删除这些缓存
     *      c. 弱引用(回收认定时加入引用队列): WeakReference, 只被弱引用引用的对象无论堆内存是否足够, JVM都会回收这些对象。
     *          弱引用本身的特点: 比软引用具有更短的生命周期(但还是有用, 因为垃圾回收线程是比较低级的线程)、
     *                          get()会返回该对象的强引用(回收后会返回null)、在对象被认定为需要被垃圾回收时, 该弱引用会被放入到引用队列中、
     *                          isEnQueued()用于判断该对象是否被认定为需要被垃圾回收。
     *          弱引用的作用: 用于规范化Map, 防止内存泄露, 如ThreadLocal。
     *      d. 虚引用(回收后加入引用队列): PhantomReference, 只被虚引用的对象跟没有被引用几乎是一样的, 随时可能会被垃圾回收器回收
     *          虚引用本身的特点: get()获取该对象的强引用时永远为null、必须与引用队列一起使用、
     *                          当垃圾回收器回收该对象后, 会将该虚引用加入引用队列, 用于判断之前引用的对象是否已经被回收了, 而程序判断到如果还有虚引用,
     *                          还可以复活这个对象。
     *          虚引用的作用: 用于检测所引用的对象是否已经被回收了, 跟踪垃圾回收的过程(比如回收、复活、回收)。
     *      e. 个人心得:
     *          java中谈引用是从内存分析的角度思考的，分析引用指向那个对象，这种引用的对象到底什么时候可以被回收等等。而谈的‘指针’是从数据结构的角度思考，
     *          比如这个链表头指针移动到哪里哪里等等，实际上移动到哪里哪里，也还是引用的赋值，因为从内存的角度来说，也就是把新的头节点的引用赋值给原来的头节点，
     *          因此也还是引用，所以java没有指针，只有引用。
     */
    public static void main(String[] args) throws InterruptedException {
        // 引用一开始为null, 后来p为非null时, o并不会等于p引用的新地址, 此前的赋值如果为null, 则跟赋值为null没什么两样, 因为此时的p不会有任何的地址指向
        Object p = null;
        Object o = p;
        p = new Object();


        Example ex = new Example();// i为0
        A a = new A();
        Example ex1 = a.add0(ex);// i为1
        a.add1(ex);// i为2
        a.modify0(ex);// i为3
        // ex一开始为501地址
        a.modify1(ex);// i为3
        Example ex2 = // i为1
                a.modify2(ex);// i为3
        Example ex3 = // i为4
                a.modify3(ex);// i为4
        Example ex4;// 会被编译器直接无视掉
//        System.err.println(ex4);// 编译器会报编译错误, ex4还没被初始化
        int[][] arr2 = new int[2][];// 并不会报错, 说明为正常情况

        // 软引用
        for (int i = 0; i < 10; i++) {
            ReferenceQueue<Object> queue1 = new ReferenceQueue<>();
            SoftReference softReference = new SoftReference(new String("123"), queue1);
            System.gc();
            System.gc();
            Object o1 = softReference.get();
            System.err.println("soft ref: " + o1);// 123
            Reference<?> poll = queue1.poll();
            System.err.println("soft queue: " + poll);// null
        }

        // 弱引用
        for (int i = 0; i < 10; i++) {
            ReferenceQueue<Object> queue2 = new ReferenceQueue<>();
            WeakReference weakReference = new WeakReference(new String("123"), queue2);
            System.gc();
            System.gc();
            Object o2 = weakReference.get();
            System.err.println("weak ref: " + o2);// null
            Reference<?> poll2 = queue2.poll();
            System.err.println("weak queue: " + poll2);// java.lang.ref.WeakReference@4f023edb
        }

        // 虚引用
        for (int i = 0; i < 10; i++) {
            ReferenceQueue<Object> queue3 = new ReferenceQueue<>();
            PhantomReference phantomReference = new PhantomReference<>(new String("123"), queue3);
            System.gc();
            System.gc();
            Object o3 = phantomReference.get();
            System.err.println("phantom ref: " + o3);// null
            Reference<?> poll3 = queue3.poll();
            System.err.println("phantom queue: " + poll3);// java.lang.ref.PhantomReference@77459877
        }
    }
}

class Example {
    int i = 0;
}

class A {
    int i = 0;

    Example add0(Example e) {// i为0
        e.i++;// i为1 => 在引用副本所引用的对象, 有提供操作对象的方法时, 该引用副本会修改原引用的对象, 比如StringBuilder.append(i);
        return e;// i为1
    }

    void add1(Example e) {// i为1
        e.i++;// i为2 => 在引用副本所引用的对象, 有提供操作对象的方法时, 该引用副本会修改原引用的对象, 比如StringBuilder.append(i);
    }

    void modify0(Example e) {// i为2
        Example ne = e;// i为2  => 对象变量的赋值, 为引用赋值, 即新变量和老变量具有相同的引用。
        ne.i++;// i为3 => 在引用副本所引用的对象, 有提供操作对象的方法时, 该引用副本会修改原引用的对象, 比如StringBuilder.append(i);
    }

    /**
     * 测试结果:
     */
    void modify1(Example e) {// i为3
        e = new Example();// i为0 => 对象变量的赋值, 为引用赋值, 即新变量(e)和老变量(new Example())具有相同的引用。
        e.i++;// i为1 => 在引用副本被更改后, 不会影响到原引用的值。
    }

    Example modify2(Example e) {// i为3
        e = new Example();// i为0 => 对象变量的赋值, 为引用赋值, 即新变量(e)和老变量(new Example())具有相同的引用。
        e.i++;// i为1 => 在引用副本被更改后, 不会影响到原引用的值。
        return e;// i为1
    }

    Example modify3(Example e) {// i为3
        Example ne = e;// i为3  => 对象变量的赋值, 为引用赋值, 即新变量和老变量具有相同的引用。
        ne.i++;// i为4 => 在引用副本所引用的对象, 有提供操作对象的方法时, 该引用副本会修改原引用的对象, 比如StringBuilder.append(i);
        return ne;// i为4
    }
}

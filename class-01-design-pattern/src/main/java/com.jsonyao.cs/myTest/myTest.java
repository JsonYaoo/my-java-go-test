package com.jsonyao.cs.myTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试类
 */
public class myTest {

    public static void main(String[] args) throws InterruptedException {
//        Integer.valueOf(1);
//
//        int a = 9;
//        int b = 1;
//        while (true) {
//            Thread.sleep(1000);
//            Map<String,String> map = new HashMap<>();
//            for (int i = a*(b-1)+1;i<=a*b;i++){
//                String key = String.valueOf(i * (1<<16));
//                map.put(String.valueOf(key),String.valueOf(key));
//            }
//            b++;
//            List<Map.Entry<String, String>> entries = new ArrayList<>(map.entrySet());
//            for (int i = 0; i < entries.size(); i++) {
//                System.out.print(entries.get(i).getKey());
//                System.out.print("\t");
//            }
//            System.out.println();
//        }

        // JDK 8
        Map<Integer, Integer> map = new HashMap<>();

        Integer key;
        map.put((key = 1 + 16 * 1), key);// key.hashCode() == 17 => index = 1的桶, 当前桶链表: 17, 当前Node指针: 17

        map.put((key = 2 + 16 * 1), key);// key.hashCode() == 18 => index = 2的桶, 当前桶链表: 18, 当前Node指针: 17 -> 18

        map.put((key = 3 + 16 * 1), key);// key.hashCode() == 19 => index = 3的桶, 当前桶链表: 19, 当前Node指针: 17 -> 18 -> 19

        map.put((key = 1 + 16 * 0), key);// key.hashCode() == 1  => index = 1的桶, 当前桶链表: 17 -> 1, 当前Node指针: 17 -> 1 -> 18 -> 19

        map.put((key = 1 + 16 * 2), key);// key.hashCode() == 33 => index = 1的桶, 当前桶链表: 17 -> 1 -> 33, 当前Node指针: 17 -> 1 -> 33 -> 18 -> 19

        // A. 65536 hashCode:     0000, 0000, 0000, 0001, 0000, 0000, 0000, 0000
        // B. 65536 hash >>> 16:  0000, 0000, 0000, 0000, 0000, 0000, 0000, 0001
        // 65536 hash = A ^ B:    0000, 0000, 0000, 0000, 0000, 0000, 0000, 0001
        // index = hash & 0x1111: 0000, 0000, 0000, 0000, 0000, 0000, 0000, 1111 => index = 1
        map.put((key = 1 << 16), key);// key.hashCode() == 65536 => index = 1的桶, 当前桶链表: 17 -> 1 -> 33 -> 65536, 当前Node指针: 17 -> 1 -> 33 -> 65536 -> 18 -> 19

        // C. 65536 hashCode:     0000, 0000, 0000, 0100, 0000, 0000, 0000, 0000
        // D. 65536 hash >>> 16:  0000, 0000, 0000, 0000, 0000, 0000, 0000, 0100
        // 65540 hash = C ^ D:    0000, 0000, 0000, 0100, 0000, 0000, 0000, 0100
        // index = hash & 0x1111: 0000, 0000, 0000, 0000, 0000, 0000, 0000, 1111 => index = 4
        map.put((key = 4 + (1 << 16)), key);// key.hashCode() == 65540 => index = 4的桶, 当前桶链表: 65540, 当前Node指针: 17 -> 1 -> 33 -> 65536 -> 18 -> 19 -> 65540

        map.put((key = 3 + 16 * 2), key);// key.hashCode() == 35 => index = 3的桶, 当前桶链表: 19 -> 35, 当前Node指针: 17 -> 1 -> 33 -> 65536 -> 18 -> 19 -> 35 -> 65540

        for(Map.Entry<Integer, Integer> entry : map.entrySet()){
            System.out.print(entry.getKey() + "\t");
        }
    }

}

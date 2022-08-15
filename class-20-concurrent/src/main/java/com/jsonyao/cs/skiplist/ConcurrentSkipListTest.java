package com.jsonyao.cs.skiplist;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.locks.LockSupport;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 测试ConcurrentSkipList
 *
 * @author yaocs2
 * @since 2021-07-02
 */
public class ConcurrentSkipListTest {

    /**
     * 提取混合字符串中的连续数字
     *
     * @param mixedStr  数字+字母混合字符串
     * @param digit     连续数字的最少个数
     * @return
     */
    public static List<String> getExtractedNumbers(String mixedStr, int digit) {
        List<String> result = new ArrayList<>();
        if(mixedStr == null) return result;

        // 正则匹配
        Pattern pattern = Pattern.compile(String.format("\\d{%s,}", digit < 1? 1 : digit));
        Matcher matcher = pattern.matcher(mixedStr);

        // 设置连续匹配结果
        while (matcher.find()) {
            result.add(matcher.group());
        }

        return result;
    }

    public static void main(String[] args) {
        List<String> extractedNumbers = getExtractedNumbers("1234321", 1);
        System.err.println(extractedNumbers);
    }
}

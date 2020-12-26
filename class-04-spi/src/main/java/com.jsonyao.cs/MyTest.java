package com.jsonyao.cs;

import java.util.Iterator;
import java.util.ServiceLoader;

public class MyTest {

    public static void main(String[] args) {
//        IParseDoc iParseDoc = new ExcelParse();
//        iParseDoc.parse();
        testSpi();
    }

    public static void testSpi() {
        ServiceLoader<IParseDoc> loadService = ServiceLoader.load(IParseDoc.class);

        Iterator<IParseDoc> iterator = loadService.iterator();
        while (iterator.hasNext()){
            iterator.next().parse();
        }
    }

}

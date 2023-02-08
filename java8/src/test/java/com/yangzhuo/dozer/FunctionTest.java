package com.yangzhuo.dozer;

import org.junit.Test;

import java.util.function.Consumer;

public class FunctionTest {

    @Test
    public void test() {
        operatorString("welcome", s -> System.out.println(s));
        operatorString("welcome", s -> System.out.println(s), s -> System.out.println(new StringBuilder(s).reverse().toString()));
    }

    private static void operatorString(String name, Consumer<String> con) {
        con.accept(name);
    }

    private static void operatorString(String name, Consumer<String> con1, Consumer<String> con2) {
        con1.andThen(con2).accept(name);
    }

    @Test
    public void printInfo() {
        String[] strArr = {"李峥:21", "李薇:18", "李苍:10"};
        printInfo(strArr, s -> System.out.print("姓名:" + s.split(":")[0]), s -> System.out.println(",年龄:" + s.split(":")[1]));
    }

    private static void printInfo(String[] strArray, Consumer<String> con1, Consumer<String> con2) {
        for (String str : strArray) {
            con1.andThen(con2).accept(str);
        }
    }
}

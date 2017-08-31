package org.inlighting.gentle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test {
    public static void main(String[] args) {
        System.out.println(int.class.getName());
        System.out.println(Integer.class.getName());
    }

    public void say(int a, String b, String c, int d) {
        System.out.println(a+";"+b+";"+c+";"+d);
    }

    public void to(int a) {
        System.out.println(a);
    }
}



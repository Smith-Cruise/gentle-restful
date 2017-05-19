package com.app.test;

/**
 * Created by Smith on 2017/5/19.
 */
public class People {
    public synchronized static void hello() {
        System.out.println("hello");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void world() {
        System.out.println("world");
    }
}

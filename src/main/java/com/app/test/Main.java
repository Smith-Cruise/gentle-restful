package com.app.test;

/**
 * Created by Smith on 2017/5/19.
 */
public class Main {
    public static void main(String[] args) {
        for (int i=0; i<10; i++) {
            new Robot(i).start();
        }
    }
}

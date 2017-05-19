package com.app.test;

/**
 * Created by Smith on 2017/5/19.
 */
public class Robot extends Thread {
    private int i = 0;

    public Robot(int a) {
        i = a;
    }
    @Override
    public void run() {
        if (i%2==0)
            People.world();
        else
            People.hello();
    }
}

package app.test;

/**
 * Created by Smith on 2017/5/19.
 */
public class Main {

}

class Robot extends Thread {
    @Override
    public void run() {

    }
}

class People {
    public synchronized void hello() {
        say();
        System.out.println("hello");
    }

    public void say() {
        System.out.println("say");
    }

    public synchronized void world() {
        System.out.println("world");
    }
}

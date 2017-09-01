package function;

@FunctionalInterface
public interface ExInterface {
    default void doSomething() {
        System.out.println("do something");
    }

    static void doStatic() {
        System.out.println("static");
    }

    void target();
}

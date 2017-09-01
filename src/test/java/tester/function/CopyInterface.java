package function;

public interface CopyInterface {
    default void doSomething() {
        System.out.println("copy");
    }
}

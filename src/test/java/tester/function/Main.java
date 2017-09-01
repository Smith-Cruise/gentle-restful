package function;

public class Main{
    public static void main(String[] args) {
        d(() -> System.out.println("fe"));
    }

    public static void d(ExInterface main) {
        main.target();
    }
}

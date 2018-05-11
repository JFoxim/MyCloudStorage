package app;

import java.util.UUID;

public class myTest {
    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString());
        System.out.println(UUID.randomUUID().toString().length());
        String str = "1234";
        System.out.println(str.hashCode());
    }
}

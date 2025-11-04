package javaprogramming.demo9Implementcustomexceptions;

public class NegativeNumberException extends Exception {
    public static void main(String[] args) {
        int a = -10;
        if (a < 0) {
            throw new ArithmeticException("a不能小于0");
        }
    }
}

package javaprogramming.demo6calculator;

public class Calculator {
    public static double calculate(double a, char op, double b){
        switch (op){
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                return a / b;
            default:
                return 0;
        }
    }
}

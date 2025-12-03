package test4;

public class Demo1 {
    public static void main(String[] args) throws MyException {
        int result = divide(4,2);
        System.out.println(result);

    }
    public static int divide(int a, int b) throws MyException {
        if (b == 0) {
            throw new MyException("除数为0");
        }
        return a / b;
    }
}

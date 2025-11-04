package javaprogramming.demo13outputarrayreverseorder;

public class ReverseOrder {
    public static void ArrayReverseOrder(int[] array){
        for (int i = array.length - 1; i >= 0; i--) {
            System.out.print(array[i] + "\t");
        }
    }
}

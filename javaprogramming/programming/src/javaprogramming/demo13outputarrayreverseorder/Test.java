package javaprogramming.demo13outputarrayreverseorder;

public class Test {
    public static void main(String[] args) {
        //目标：编写一个程序，逆序输出一个整型数组中的元素。
        int[] arr = {1,2,3,4,5,6,7,8,9,10};
        System.out.println("顺序：");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "\t");
        }
        System.out.println("\n逆序：");
        ReverseOrder.ArrayReverseOrder(arr);
    }
}

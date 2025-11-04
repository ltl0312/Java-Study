package javaprogramming.demo16mergearrays;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {
        //目标：编写程序，合并两个有序整型数组为一个有序数组。
        int[] arr1 = {1,2,3,4,5,6,7,8,9,10};
        int[] arr2 = {11,12,13,14,15,16,17,18,19,20};
        //1.将数组一和二拷贝到数组三
        int[] arr3 = IntStream
                .concat(Arrays.stream(arr1), Arrays.stream(arr2))
                .sorted()
                .toArray();
        System.out.println(Arrays.toString(arr3));
    }
}

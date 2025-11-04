package javaprogramming.demo13outputarrayreverseorder;

public class ReverseOrder {
    public static void ArrayReverseOrder(int[] array){
        // 使用冒泡排序实现数组从大到小排序
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                // 如果前面的元素小于后面的元素，则交换位置
                if (array[j] < array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }

        // 输出排序后的数组
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + "\t");
        }
    }
}

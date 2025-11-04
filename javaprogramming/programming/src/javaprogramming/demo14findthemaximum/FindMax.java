package javaprogramming.demo14findthemaximum;

public class FindMax<T extends Comparable<T>> {
    //泛型方法
    public T findMax(T[] arr) {
        T max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (max.compareTo(arr[i]) < 0){
                max = arr[i];
            }
        }
        return max;
    }
}

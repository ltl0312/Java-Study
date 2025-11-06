package javaprogramming.demo27treesetsort;

import java.util.Comparator;
import java.util.TreeSet;

public class AutoSort implements Comparator<Integer> {

    public static void sort(TreeSet<Integer> set) {
        Object[] arr = set.toArray();
        //冒泡排序
        for (int i = 0; i < set.size() - 1; i++) {
            for (int j = 0; j < set.size() - 1 - i; j++) {
                if ((Integer) arr[j] - (Integer) arr[j + 1] > 0) {
                    Integer temp = (Integer) set.toArray()[j];
                    set.toArray()[j] = set.toArray()[j + 1];
                    set.toArray()[j + 1] = temp;
                }
            }
        }

    }

    @Override
    public int compare(Integer o1, Integer o2) {
        return o1 - o2;
    }
}

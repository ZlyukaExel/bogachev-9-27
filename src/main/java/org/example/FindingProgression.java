package org.example;

import java.util.Arrays;
import java.util.List;

public class FindingProgression {
    /*public static void main(String[] Args){
        process(Arrays.asList(3, 8, 5, 4, 9, 6, 7, 1, 2));
        process(Arrays.asList(36338, 8, 5, 10, 9, 6, 77247835, 1, 2));
        process(Arrays.asList(8, 8));
    }*/
    public static List<Integer> integerList;

    public static void process(List<Integer> list) {
        System.out.print(list + "   ->   ");
        integerList = list;

        if (isProgression(list))
            sort(list);
        System.out.println(integerList);
    }

    public static int findMax(List<Integer> list) {
        int max = Integer.MIN_VALUE;
        for (Integer integer : list) {
            if (integer > max) {
                max = integer;
            }
        }
        return max;
    }

    public static int findMin(List<Integer> list) {
        int min = Integer.MAX_VALUE;
        for (Integer integer : list) {
            if (integer < min) {
                min = integer;
            }
        }
        return min;
    }

    public static int indexOf(List<Integer> list, int value) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == value) {
                return i;
            }
        }
        return -1;
    }

    public static void sort(List<Integer> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j) > list.get(j + 1)) {
                    swap(list, j, j + 1);
                }
            }
        }
    }

    public static void swap(List<Integer> list, int i, int j) {
        int temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    public static boolean isProgression(List<Integer> list) {
        int min = findMin(list);
        int max = findMax(list);
        int n = list.size();

        if ((max - min) % (n - 1) != 0) return false;

        int d = (max - min) / (n - 1);

        for (int i = 0; i < n; i++) {
            int expectedValue = min + i * d;
            if (indexOf(list, expectedValue) == -1) {
                return false;
            }
        }
        return true;
    }
}

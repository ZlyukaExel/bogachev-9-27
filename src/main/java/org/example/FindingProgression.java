package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FindingProgression {
    /*public static void main(String[] Args){
        process(Arrays.asList(3, 8, 5, 4, 9, 6, 7, 1, 2));
        process(Arrays.asList(36338, 8, 5, 10, 9, 6, 77247835, 1, 2));
    }*/
    public static List<Integer> integerList;

    public static void process(List<Integer> list){
        List<Integer> sortedList = new ArrayList<>(list);
        sort(sortedList);
        int frst = findMin(sortedList);
        int scnd = indexOf(sortedList, 1);
        int d = scnd - frst;
        int length = sortedList.size();
        boolean isProgression = true;

        for (int i = 0; i < length - 1; i++) {
            int valOfI = indexOf(sortedList, i);
            int valOfNextI = indexOf(sortedList, i + 1);
            if (valOfI + d != valOfNextI)
            {
                isProgression = false;
                break;
            }
        }

        if(isProgression)
            integerList = sortedList;
        else
            integerList = list;

        print(list);
        System.out.print("   ->   ");
        print(integerList);
        System.out.println();
    }

    public static int findMax(List<Integer> list){
        return list.getLast();
    }

    public static int findMin(List<Integer> list) {
        return list.getFirst();
    }

    public static int indexOf(List<Integer> list, int value){
        return list.get(value);
    }

    public static void sort(List<Integer> list){
        Collections.sort(list);
    }

    public static void print(List<Integer> list){
        for(Integer v : list){
            System.out.print(v + " ");
        }
    }
}

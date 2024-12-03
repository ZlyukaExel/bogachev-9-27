package org.example;

import java.util.Collections;
import java.util.List;

public class FindingProgression {

    public static void process(List<Integer> list){
        List<Integer> sortedList = list;
        sort(sortedList);
        int mn = findMin(sortedList);
        int mx = findMax(sortedList);
        int d = mx - mn;
        int length = sortedList.size();
        boolean isProgression = true;

        for (int i = 0; i < length; i++) {
            if (indexOf(sortedList, i) != mn + d * i)
            {
                isProgression = false;
                break;
            }
        }

        if(isProgression)
            System.out.println("Okay it`s progression"); //==================================
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
}

package com.touyan.investment.helper;

import com.touyan.investment.bean.user.Subscriber;

import java.util.Comparator;

public class HanziComp implements Comparator {

    public int compare(Object o1, Object o2) {
        String sort1 = ((Subscriber) o1).getNameSort();
        String sort2 = ((Subscriber) o2).getNameSort();
        return sort1.compareTo(sort2);
    }


}


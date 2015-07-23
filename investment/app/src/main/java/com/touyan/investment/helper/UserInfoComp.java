package com.touyan.investment.helper;

import com.touyan.investment.bean.user.Subscriber;
import com.touyan.investment.bean.user.UserInfo;

import java.util.Comparator;

/**
 * Created by Administrator on 2015/7/23.
 */
public class UserInfoComp implements Comparator {
    public int compare(Object o1, Object o2) {
        String sort1 = ((UserInfo) o1).getNameSort();
        String sort2 = ((UserInfo) o2).getNameSort();
        return sort1.compareTo(sort2);
    }
}

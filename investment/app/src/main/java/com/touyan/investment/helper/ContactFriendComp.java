package com.touyan.investment.helper;

import com.touyan.investment.bean.message.ContactFriend;
import com.touyan.investment.bean.user.UserInfo;

import java.util.Comparator;

/**
 * Created by Administrator on 2015/7/28.
 */
public class ContactFriendComp implements Comparator {
    public int compare(Object o1, Object o2) {
        String sort1 = ((ContactFriend) o1).getUserinfo().getNameSort();
        String sort2 = ((ContactFriend) o2).getUserinfo().getNameSort();
        return sort1.compareTo(sort2);
    }
}

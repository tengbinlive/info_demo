package com.touyan.investment.bean.main;

import com.core.openapi.OpenApiSimpleResult;
import com.touyan.investment.bean.user.UserInfo;

public class InvInfoDetailResult extends OpenApiSimpleResult {

    private String replyCount;//评价总数
    private String isBuy; //是否购买过  0 未购买，1购买
    private String isStore; //是否收藏过 0 未收藏，1收藏

    private UserInfo info;

    public String getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(String replyCount) {
        this.replyCount = replyCount;
    }

    public String getIsBuy() {
        return isBuy;
    }

    public void setIsBuy(String isBuy) {
        this.isBuy = isBuy;
    }

    public String getIsStore() {
        return isStore;
    }

    public void setIsStore(String isStore) {
        this.isStore = isStore;
    }

    public UserInfo getInfo() {
        return info;
    }

    public void setInfo(UserInfo info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "InvInfoDetailResult{" +
                "replyCount='" + replyCount + '\'' +
                ", isBuy='" + isBuy + '\'' +
                ", isStore='" + isStore + '\'' +
                ", info=" + info +
                '}';
    }
}

package com.touyan.investment.bean.message;

import com.easemob.chat.EMConversation;

import java.io.Serializable;

public class ConversationBean implements Serializable {

    private EMConversation conversation;
    private Object object;


    public EMConversation getConversation() {
        return conversation;
    }

    public void setConversation(EMConversation conversation) {
        this.conversation = conversation;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "ConversationBean{" +
                "conversation=" + conversation +
                ", object=" + object +
                '}';
    }
}

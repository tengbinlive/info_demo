package com.touyan.investment.event;

/**
 * Created by Administrator on 2015/7/23.
 */
public class NewMessageEvent {

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "NetworkEvent{" +
                "status='" + status + '\'' +
                '}';
    }
}

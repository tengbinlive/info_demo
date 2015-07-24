package com.touyan.investment.event;

/**
 * Created by bin on 15/7/23.
 */
public class ConnectionEventType {
    public ConnectionEventType(int status) {
        this.status = status;
    }

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

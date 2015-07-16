package com.touyan.investment.bean.user;

import com.core.openapi.OpenApiSimpleResult;

public class AccountResult extends OpenApiSimpleResult {

    private Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "AccountResult{" +
                "account=" + account +
                '}';
    }
}

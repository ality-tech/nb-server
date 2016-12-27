package org.neighbor.api.dtos;

import com.google.common.base.MoreObjects;

public class CreateUserRequest {
    private String accountUrn;
    private String login;
    private String pinCode;
    private String userPhone;

    public String getAccountUrn() {
        return accountUrn;
    }

    public void setAccountUrn(String accountUrn) {
        this.accountUrn = accountUrn;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("accountUrn", accountUrn)
                .add("login", login)
                .add("pinCode", pinCode)
                .add("userPhone", userPhone)
                .toString();
    }
}

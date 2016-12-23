package org.neighbor.api.dtos;

import com.google.common.base.MoreObjects;

public class AuthRegisterRequest {
    private String extId;
    private String accountNumber;
    private String login;
    private String pinCode;
    private String userPhone;

    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
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
                .add("extId", extId)
                .add("accountNumber", accountNumber)
                .add("login", login)
                .add("pinCode", pinCode)
                .add("userPhone", userPhone)
                .toString();
    }
}

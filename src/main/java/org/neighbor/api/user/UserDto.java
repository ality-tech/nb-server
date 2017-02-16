package org.neighbor.api.user;

import com.google.common.base.MoreObjects;

import java.util.Date;
import java.util.Objects;

public class UserDto {

    private String accountId;//account_urn
    private String userUrn;
    private String login;
    private String userPhone;
    private String activationStatus;
    private Date createdOn;
    private Date updatedOn;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUserUrn() {
        return userUrn;
    }

    public void setUserUrn(String userUrn) {
        this.userUrn = userUrn;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getActivationStatus() {
        return activationStatus;
    }

    public void setActivationStatus(String activationStatus) {
        this.activationStatus = activationStatus;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("accountId", accountId)
                .add("userUrn", userUrn)
                .add("login", login)
                .add("userPhone", userPhone)
                .add("activationStatus", activationStatus)
                .add("createdOn", createdOn)
                .add("updatedOn", updatedOn)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(accountId, userDto.accountId) &&
                Objects.equals(userUrn, userDto.userUrn) &&
                Objects.equals(login, userDto.login) &&
                Objects.equals(userPhone, userDto.userPhone) &&
                Objects.equals(activationStatus, userDto.activationStatus) &&
                Objects.equals(createdOn, userDto.createdOn) &&
                Objects.equals(updatedOn, userDto.updatedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, userUrn, login, userPhone, activationStatus, createdOn, updatedOn);
    }
}

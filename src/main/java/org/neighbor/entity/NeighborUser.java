package org.neighbor.entity;

import com.google.common.base.MoreObjects;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class NeighborUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="account_id")
    private Long accountId;
    @ManyToOne(targetEntity = NeighborAccount.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", insertable = false, updatable = false)
    private NeighborAccount account;
    private String userUrn;
    private String login;
    private String pinCode;
    private String userPhone;
    @Enumerated(EnumType.STRING)
    private ActivationStatus activationStatus;
    private Date createdOn;
    private Date updatedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public NeighborAccount getAccount() {
        return account;
    }

    public void setAccount(NeighborAccount account) {
        this.account = account;
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

    public ActivationStatus getActivationStatus() {
        return activationStatus;
    }

    public void setActivationStatus(ActivationStatus activationStatus) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NeighborUser that = (NeighborUser) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(accountId, that.accountId) &&
                Objects.equals(userUrn, that.userUrn) &&
                Objects.equals(login, that.login) &&
                Objects.equals(pinCode, that.pinCode) &&
                Objects.equals(userPhone, that.userPhone) &&
                activationStatus == that.activationStatus &&
                Objects.equals(createdOn, that.createdOn) &&
                Objects.equals(updatedOn, that.updatedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountId, userUrn, login, pinCode, userPhone, activationStatus, createdOn, updatedOn);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("accountId", accountId)
                .add("userUrn", userUrn)
                .add("login", login)
                .add("pinCode", pinCode)
                .add("userPhone", userPhone)
                .add("activationStatus", activationStatus)
                .add("createdOn", createdOn)
                .add("updatedOn", updatedOn)
                .toString();
    }
}

package org.neighbor.server.entity;

import com.google.common.base.MoreObjects;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity(name = "neighbor_account")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "org_id")
    private Long orgId;
    @ManyToOne(targetEntity = OrgEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id", insertable = false, updatable = false)
    private OrgEntity org;
    @Column(name = "account_number")
    private String accountNumber;
    @Column(name = "account_urn")
    private String accountUrn;
    @Column(name = "owner_phone")
    private String ownerPhone;
    @Column(name = "address_street")
    private String addressStreet;
    @Column(name = "address_building")
    private String addressBuilding;
    @Column(name = "address_floor")
    private String addressFloor;
    @Column(name = "address_flat")
    private String addressFlat;
    @Column(name = "created_on")
    private Date createdOn;
    @Column(name = "updated_on")
    private Date updatedOn;
    @Column(name = "active")
    private Boolean isActive = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public OrgEntity getOrg() {
        return org;
    }

    public void setOrg(OrgEntity org) {
        this.org = org;
        this.orgId = org.getId();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountUrn() {
        return accountUrn;
    }

    public void setAccountUrn(String accountUrn) {
        this.accountUrn = accountUrn;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressBuilding() {
        return addressBuilding;
    }

    public void setAddressBuilding(String addressBuilding) {
        this.addressBuilding = addressBuilding;
    }

    public String getAddressFloor() {
        return addressFloor;
    }

    public void setAddressFloor(String addressFloor) {
        this.addressFloor = addressFloor;
    }

    public String getAddressFlat() {
        return addressFlat;
    }

    public void setAddressFlat(String addressFlat) {
        this.addressFlat = addressFlat;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountEntity that = (AccountEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(orgId, that.orgId) &&
                Objects.equals(accountNumber, that.accountNumber) &&
                Objects.equals(accountUrn, that.accountUrn) &&
                Objects.equals(ownerPhone, that.ownerPhone) &&
                Objects.equals(addressStreet, that.addressStreet) &&
                Objects.equals(addressBuilding, that.addressBuilding) &&
                Objects.equals(addressFloor, that.addressFloor) &&
                Objects.equals(addressFlat, that.addressFlat) &&
                Objects.equals(createdOn, that.createdOn) &&
                Objects.equals(updatedOn, that.updatedOn) &&
                Objects.equals(isActive, that.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orgId, accountNumber, accountUrn, ownerPhone, addressStreet, addressBuilding, addressFloor, addressFlat, createdOn, updatedOn, isActive);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("orgId", orgId)
                .add("accountNumber", accountNumber)
                .add("accountUrn", accountUrn)
                .add("ownerPhone", ownerPhone)
                .add("addressStreet", addressStreet)
                .add("addressBuilding", addressBuilding)
                .add("addressFloor", addressFloor)
                .add("addressFlat", addressFlat)
                .add("createdOn", createdOn)
                .add("updatedOn", updatedOn)
                .add("isActive", isActive)
                .toString();
    }
}

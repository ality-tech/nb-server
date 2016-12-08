package org.neighbor.entity;

import com.google.common.base.MoreObjects;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class NeighborAccount {

    private Long id;
    @Column(name = "org_id")
    private Long orgId;
    @ManyToOne(targetEntity = NeighborOrg.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id", insertable = false, updatable = false)
    private NeighborOrg org;
    private String accountNumber;
    private String accountUrn;
    private String ownerPhone;
    private String addressStreet;
    private String addressBuilding;
    private String addressFloor;
    private String addressFlat;
    private Date createdOn;
    private Date updatedOn;
    private Boolean isActive = true;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "org_id")
    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id", updatable = false, insertable = false)
    public NeighborOrg getOrg() {
        return org;
    }

    public void setOrg(NeighborOrg org) {
        this.org = org;
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
        NeighborAccount that = (NeighborAccount) o;
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

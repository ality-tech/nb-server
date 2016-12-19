package org.neighbor.api.dtos;

public class CreateAccountRequest {
    private String accountNumber;
    private String orgExtId;
    private String ownerPhone;
    private String addressStreet;
    private String addressBuilding;
    private String addressFloor;
    private String addressFlat;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getOrgExtId() {
        return orgExtId;
    }

    public void setOrgExtId(String orgExtId) {
        this.orgExtId = orgExtId;
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
}

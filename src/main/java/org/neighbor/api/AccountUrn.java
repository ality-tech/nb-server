package org.neighbor.api;

import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;

public class AccountUrn {
    private String urn;
    private Long orgId;
    private String extId;
    private String accountNumber;

    public AccountUrn(String urn) {
        if (StringUtils.isEmpty(urn)) throw new RuntimeException("Urn is empty");
        String[] strings = urn.split(":");
        if (strings.length < 4) throw new RuntimeException("Urn is invalid");

        this.accountNumber = strings[3];
        this.extId = strings[2];
        this.orgId = NumberUtils.parseNumber(strings[1], Long.class);
        this.urn = strings[0];
    }

    public String getUrn() {
        return urn;
    }

    public void setUrn(String urn) {
        this.urn = urn;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

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

}

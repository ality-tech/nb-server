package org.neighbor.api.account;

import org.springframework.util.StringUtils;

public class AccountUrn {
    private String extId;
    private String accountNumber;

    //todo refactor me
    public AccountUrn(String urn) {
        if (StringUtils.isEmpty(urn)) throw new RuntimeException("Urn is empty");
        String[] strings = urn.split(":");
        if (strings.length < 2) throw new RuntimeException("Urn is invalid");
        this.accountNumber = strings[1];
        this.extId = strings[0];
    }

    public String getUrn() {
        return extId + ":" + accountNumber;
    }

    public String getExtId() {
        return extId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }


}

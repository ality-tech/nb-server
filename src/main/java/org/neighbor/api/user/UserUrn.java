package org.neighbor.api.user;

import org.neighbor.api.account.AccountUrn;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserUrn {
    private String login;
    private AccountUrn accountUrn;

    private static final Pattern pattern = Pattern.compile(":(?:.(?!:))+$");

    //// FIXME: refactor me
    public UserUrn(String urn) {
        if (StringUtils.isEmpty(urn)) throw new RuntimeException("Urn is empty");
        Matcher matcher = pattern.matcher(urn);
        if (matcher.find())
            login = matcher.group().replace(":", "");
        accountUrn = new AccountUrn(urn);
    }

    public String getUrn() {
        return login + ":" + accountUrn.getUrn();
    }

    public String getLogin() {
        return login;
    }

    public AccountUrn getAccountUrn() {
        return accountUrn;
    }

}

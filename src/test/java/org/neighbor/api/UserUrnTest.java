package org.neighbor.api;

import org.junit.Assert;
import org.junit.Test;

public class UserUrnTest {

    @Test
    public void shouldParseUrn() {
        String urn = "aaa:111:eee";
        UserUrn userUrn = new UserUrn(urn);
        Assert.assertEquals("eee", userUrn.getLogin());
        Assert.assertEquals("111", userUrn.getAccountUrn().getAccountNumber());
        Assert.assertEquals("aaa", userUrn.getAccountUrn().getExtId());
    }



}
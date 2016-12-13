package org.neighbor.api;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserUrnTest {

    @Test
    public void shouldParseUrn() {
        String urn = "aaa:111:ccc:ddd:eee";
        UserUrn userUrn = new UserUrn(urn);
        Assert.assertEquals("eee", userUrn.getLogin());
    }



}
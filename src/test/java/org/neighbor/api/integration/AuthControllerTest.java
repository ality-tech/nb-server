package org.neighbor.api.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.neighbor.api.GeneralResponse;
import org.neighbor.api.dtos.AuthCheckRequest;
import org.neighbor.controller.AuthController;
import org.neighbor.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class AuthControllerTest {

    @Autowired
    private AuthController authController;

    /*
        /auth/check
        accepts login + pin_code (hash)
        if user not exists throws an exception: USER_NOT_REGISTERED
        is user blocked throws as exception: USED_BLOCKED
        returns empty response with 200 status on success
     */

    @Test
    public void shouldAcceptLoginPin() {
        //todo create user before this test!
        AuthCheckRequest request = new AuthCheckRequest();
        String hash = Base64Utils.encodeToString(("login" + AuthService.SEPARATOR + "password").getBytes());
        request.setHash(hash);
        ResponseEntity<GeneralResponse> response = authController.check(request);
    }

    /*
        /auth/registeraccepts user information + ext_id + account_number
        if login already registered throw an exception with code:
        USER_ACTIVE/REGISTRATION_REQUESTED/USER_BLOCKED
        check if org_id valid (exists and not blocked)
        check that nb_account exists and not blocked
        create are user + bn_user_status_history entry in 'REQUESTED' state + new nb_activation_token entry with
        sha1(random uuid) token with blank valid_to date
        default role attached to user: 'nb_user'
     */

}

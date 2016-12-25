package org.neighbor.api.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.neighbor.api.ErrorCode;
import org.neighbor.api.GeneralResponse;
import org.neighbor.api.JsonError;
import org.neighbor.api.dtos.*;
import org.neighbor.controller.AccountController;
import org.neighbor.controller.AuthController;
import org.neighbor.controller.OrgController;
import org.neighbor.entity.*;
import org.neighbor.repository.RoleRepository;
import org.neighbor.repository.TokenRepository;
import org.neighbor.repository.UserHistoryRepository;
import org.neighbor.repository.UserRepository;
import org.neighbor.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class AuthControllerTest {

    @Autowired
    private AuthController authController;
    @Autowired
    private OrgController orgController;
    @Autowired
    private AccountController accountController;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserHistoryRepository userHistoryRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private RoleRepository roleRepository;

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
        USER_ACTIVE/REGISTRATION_REQUESTED/USER_BLOCKED_ERROR
        check if org_id valid (exists and not blocked)
        check that nb_account exists and not blocked
        create are user + bn_user_status_history entry in 'REQUESTED' state + new nb_activation_token entry with
        sha1(random uuid) token with blank valid_to date
        default role attached to user: 'nb_user'
     */

    @Test
    public void shouldRegisterUser() {
        String testOrgExtId = "test_org";
        String testAccountNumber = "existing_account";
        CreateOrgRequest org = new CreateOrgRequest();
        org.setExtId(testOrgExtId);
        org.setName("org_name");
        orgController.create(org).getBody();

        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setAccountNumber(testAccountNumber);
        createAccountRequest.setOwnerPhone("6789");
        createAccountRequest.setOrgExtId(testOrgExtId);
        GeneralResponse actualResponse = accountController.create(createAccountRequest).getBody();

        AuthRegisterRequest request = new AuthRegisterRequest();
        request.setExtId(testOrgExtId);
        request.setAccountNumber(testAccountNumber);
        request.setLogin("unique_login");
        request.setPinCode("password");
        request.setUserPhone("1234676789");
        ResponseEntity<GeneralResponse> response = authController.register(request);
        assertNotNull("should contain response", response);
        assertEquals("response should have 201 status", 201, response.getStatusCode().value());

        Optional<NeighborUser> byLogin = userRepository.findByLogin(request.getLogin());
        assertTrue("should be persisted", byLogin.isPresent());

        NeighborUser user = byLogin.get();
        List<NeighborUserStatusHistory> history = userHistoryRepository.findByUsrId(user.getId());
        assertTrue("it should be history about user", !history.isEmpty());

        List<NeighborActivationToken> tokens = tokenRepository.findByUserId(user.getId());
        assertTrue("it should be token for user", !tokens.isEmpty());

        List<NeighborRole> roles = roleRepository.findByUserId(user.getId());
        assertTrue("user should have role", !roles.isEmpty());
        assertTrue("user should have nb_user role", roles.stream().filter(r -> r.getUserRole() == RoleEnum.ROLE_NB_USER).findFirst().isPresent());
    }

    @Test
    public void shouldThrowAccNotExist() {
        String testOrgExtId = "test_org";
        String testAccountNumber = "existing_account";
        CreateOrgRequest org = new CreateOrgRequest();
        org.setExtId(testOrgExtId);
        org.setName("org_name");
        orgController.create(org).getBody();

        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setAccountNumber(testAccountNumber);
        createAccountRequest.setOwnerPhone("6789");
        createAccountRequest.setOrgExtId(testOrgExtId);
        GeneralResponse actualResponse = accountController.create(createAccountRequest).getBody();

        AuthRegisterRequest request = new AuthRegisterRequest();
        String orgExtId = testOrgExtId;
        request.setExtId(orgExtId);
        request.setAccountNumber("non_exist_account");
        request.setLogin("unique_login");
        request.setPinCode("password");
        request.setUserPhone("1234676789");
        ResponseEntity<GeneralResponse> response = authController.register(request);
        assertNotNull("should contain response", response);
        assertEquals("should have 400 code", 400, response.getStatusCode().value());
        assertNotNull("should contain error", response.getBody().getJsonError());
        GeneralResponse expectedBody = new GeneralResponse();
        expectedBody.setHttpCode(400);
        JsonError error = new JsonError();
        error.setCode(ErrorCode.ACCOUNT_NUMBER_NOT_EXIST);
        error.setMessage("account number doesn't exist");
        error.setTimestamp(response.getBody().getJsonError().getTimestamp());
        expectedBody.setJsonError(error);
        assertEquals("should correct body", expectedBody, response.getBody());
    }

    @Test
    public void shouldThrowOrgNotExist() {
        String testOrgExtId = "test_org";
        String testAccountNumber = "existing_account";
        CreateOrgRequest org = new CreateOrgRequest();
        org.setExtId(testOrgExtId);
        org.setName("org_name");
        orgController.create(org).getBody();

        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setAccountNumber(testAccountNumber);
        createAccountRequest.setOwnerPhone("6789");
        createAccountRequest.setOrgExtId(testOrgExtId);
        GeneralResponse actualResponse = accountController.create(createAccountRequest).getBody();
        //todo create existing account and org
        AuthRegisterRequest request = new AuthRegisterRequest();
        String orgExtId = testOrgExtId;
        request.setExtId("non_exist_org");
        request.setAccountNumber(testAccountNumber);
        request.setLogin("unique_login");
        request.setPinCode("password");
        request.setUserPhone("1234676789");
        ResponseEntity<GeneralResponse> response = authController.register(request);
        assertNotNull("should contain response", response);
        assertEquals("should have 400 code", 400, response.getStatusCode().value());
        assertNotNull("should contain error", response.getBody().getJsonError());
        GeneralResponse expectedBody = new GeneralResponse();
        expectedBody.setHttpCode(400);
        JsonError error = new JsonError();
        error.setCode(ErrorCode.ORG_WITH_EXTID_NOT_FOUND);
        error.setMessage("There is no org with given ext_id");
        error.setTimestamp(response.getBody().getJsonError().getTimestamp());
        expectedBody.setJsonError(error);
        assertEquals("should correct body", expectedBody, response.getBody());
    }


    /*
    /auth/confirm
        accepts login + token
        if activation_token found in status 'SENT' then
        update token activation_status to 'CONFIRMED'
        create new bn_user_status_history with 'ACTIVE' status
        update nb_user.activation_status for nb_user
        set nb_user.updated_on to current timestamp
     */
    @Test
    public void shouldConfirmRegistration() {
        String testOrgExtId = "test_org";
        String testAccountNumber = "existing_account";
        String login = "unique_login";
        CreateOrgRequest org = new CreateOrgRequest();
        org.setExtId(testOrgExtId);
        org.setName("org_name");
        orgController.create(org).getBody();

        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setAccountNumber(testAccountNumber);
        createAccountRequest.setOwnerPhone("6789");
        createAccountRequest.setOrgExtId(testOrgExtId);
        GeneralResponse actualResponse = accountController.create(createAccountRequest).getBody();

        AuthRegisterRequest request = new AuthRegisterRequest();
        request.setExtId(testOrgExtId);
        request.setAccountNumber(testAccountNumber);
        request.setLogin(login);
        request.setPinCode("password");
        request.setUserPhone("1234676789");
        ResponseEntity<GeneralResponse> registerResponse = authController.register(request);
        AuthConfirmRequest confirmRequest = new AuthConfirmRequest();
        confirmRequest.setToken(registerResponse.getBody().getToken());
        confirmRequest.setLogin(login);

        ResponseEntity<GeneralResponse> response = authController.confirm(confirmRequest);

        assertNotNull("should contain response", response);
    }


}

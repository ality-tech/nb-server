package org.neighbor.server.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.neighbor.api.*;
import org.neighbor.api.auth.AuthConfirmRequest;
import org.neighbor.api.auth.AuthRegisterRequest;
import org.neighbor.api.account.CreateAccountRequest;
import org.neighbor.api.RoleEnum;
import org.neighbor.api.organizaton.CreateOrgRequest;
import org.neighbor.api.user.ActivationStatus;
import org.neighbor.server.MainApp;
import org.neighbor.server.entity.ActivationTokenEntity;
import org.neighbor.server.entity.RoleEntity;
import org.neighbor.server.entity.UserEntity;
import org.neighbor.server.entity.UserStatusHistoryEntity;
import org.neighbor.server.repository.RoleRepository;
import org.neighbor.server.repository.TokenRepository;
import org.neighbor.server.repository.UserHistoryRepository;
import org.neighbor.server.repository.UserRepository;
import org.neighbor.server.util.ResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT, classes = MainApp.class)
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

        Optional<UserEntity> byLogin = userRepository.findByLogin(request.getLogin());
        assertTrue("should be persisted", byLogin.isPresent());

        UserEntity user = byLogin.get();
        List<UserStatusHistoryEntity> history = userHistoryRepository.findByUsrId(user.getId());
        assertTrue("it should be history about user", !history.isEmpty());

        List<ActivationTokenEntity> tokens = tokenRepository.findByUserId(user.getId());
        assertTrue("it should be token for user", !tokens.isEmpty());

        List<RoleEntity> roles = roleRepository.findByUserId(user.getId());
        assertTrue("user should have role", !roles.isEmpty());
        assertTrue("user should have nb_user role", roles.stream().filter(r -> r.getUserRole() == RoleEnum.ROLE_NB_USER).findFirst().isPresent());
    }

    @Test
    public void shouldThrowUserExistError() {
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
        accountController.create(createAccountRequest);

        String nonUniqueLogin = "nonunique_login";
        UserEntity nonUniqueUser = new UserEntity();
        nonUniqueUser.setLogin(nonUniqueLogin);
        userRepository.save(nonUniqueUser);

        AuthRegisterRequest request = new AuthRegisterRequest();
        request.setExtId(testOrgExtId);
        request.setAccountNumber(testAccountNumber);
        request.setLogin(nonUniqueLogin);
        request.setPinCode("password");
        request.setUserPhone("1234676789");

        ResponseEntity<GeneralResponse> response = authController.register(request);
        assertNotNull("should contain response", response);
        assertEquals("response should have 400 status", 400, response.getStatusCode().value());
        GeneralResponse expected = ResponseGenerator.generateUserActiveError();
        expected.getJsonError().setTimestamp(response.getBody().getJsonError().getTimestamp());
        assertEquals("response should have 400 status", expected, response.getBody());

        nonUniqueUser.setActivationStatus(ActivationStatus.ACTIVE);
        userRepository.save(nonUniqueUser);
        ResponseEntity<GeneralResponse> responseUserActive = authController.register(request);
        expected.getJsonError().setTimestamp(responseUserActive.getBody().getJsonError().getTimestamp());
        assertEquals("response should have 400 status", expected, responseUserActive.getBody());

        nonUniqueUser.setActivationStatus(ActivationStatus.BLOCKED);
        userRepository.save(nonUniqueUser);
        ResponseEntity<GeneralResponse> responseUserBlocked = authController.register(request);
        GeneralResponse userBlockedError = ResponseGenerator.generateUserBlockedError();
        userBlockedError.getJsonError().setTimestamp(responseUserBlocked.getBody().getJsonError().getTimestamp());
        assertEquals("response should have 400 status", userBlockedError, responseUserBlocked.getBody());

        nonUniqueUser.setActivationStatus(ActivationStatus.REQUESTED);
        userRepository.save(nonUniqueUser);
        ResponseEntity<GeneralResponse> responseUserRequested = authController.register(request);
        GeneralResponse userRegistrationRequestedError = ResponseGenerator.generateUserRequestedRegistrationError();
        userRegistrationRequestedError.getJsonError().setTimestamp(responseUserRequested.getBody().getJsonError().getTimestamp());
        assertEquals("response should have 400 status", userRegistrationRequestedError, responseUserRequested.getBody());
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

    @Test
    public void shouldThrowSecurityViolationOnWrongDataConfirm() {

        AuthConfirmRequest confirmRequest = new AuthConfirmRequest();
        confirmRequest.setToken("abc");
        confirmRequest.setLogin("non_exists");

        ResponseEntity<GeneralResponse> response = authController.confirm(confirmRequest);

        assertNotNull("should contain response", response);
        assertEquals("response code should be 400", 400, response.getStatusCode().value());
        GeneralResponse expectedError = ResponseGenerator.generateSecurityViolationError();
        expectedError.getJsonError().setTimestamp(response.getBody().getJsonError().getTimestamp());
        assertEquals("should return SECURITY_VIOLATION error", expectedError, response.getBody());

        ActivationTokenEntity token = new ActivationTokenEntity();
        String test_token = "test_token";
        token.setToken(test_token);
        tokenRepository.save(token);
        confirmRequest.setToken(test_token);
        ResponseEntity<GeneralResponse> responseExistTokenAbsentUser = authController.confirm(confirmRequest);
        expectedError.getJsonError().setTimestamp(responseExistTokenAbsentUser.getBody().getJsonError().getTimestamp());
        assertEquals("should return SECURITY_VIOLATION error", expectedError, responseExistTokenAbsentUser.getBody());

        token.setTokenStatus(TokenStatus.CONFIRMED);
        tokenRepository.save(token);
        ResponseEntity<GeneralResponse> responseAlreadyConfirmed = authController.confirm(confirmRequest);
        expectedError.getJsonError().setTimestamp(responseAlreadyConfirmed.getBody().getJsonError().getTimestamp());
        assertEquals("should return SECURITY_VIOLATION error", expectedError, responseAlreadyConfirmed.getBody());

        token.setTokenStatus(TokenStatus.SENT);
        tokenRepository.save(token);
        ResponseEntity<GeneralResponse> responseNonExistUser = authController.confirm(confirmRequest);
        expectedError.getJsonError().setTimestamp(responseNonExistUser.getBody().getJsonError().getTimestamp());
        assertEquals("should return SECURITY_VIOLATION error", expectedError, responseNonExistUser.getBody());

        UserEntity user = new UserEntity();
        String testUserLogin = "test_user";
        user.setLogin(testUserLogin);
        userRepository.save(user);

        UserEntity anotherUser = new UserEntity();
        anotherUser.setLogin("another_user");
        userRepository.save(anotherUser);
        token.setUserId(anotherUser.getId());
        tokenRepository.save(token);

        confirmRequest.setLogin(testUserLogin);
        ResponseEntity<GeneralResponse> responseWrongUser = authController.confirm(confirmRequest);
        expectedError.getJsonError().setTimestamp(responseWrongUser.getBody().getJsonError().getTimestamp());
        assertEquals("should return SECURITY_VIOLATION error", expectedError, responseWrongUser.getBody());
    }



}

package org.neighbor.api.integration;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neighbor.api.ErrorCode;
import org.neighbor.api.GeneralResponse;
import org.neighbor.api.JsonError;
import org.neighbor.api.dtos.CreateAccountRequest;
import org.neighbor.api.dtos.CreateOrgRequest;
import org.neighbor.controller.AccountController;
import org.neighbor.controller.OrgController;
import org.neighbor.entity.NeighborAccount;
import org.neighbor.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class AccountControllerTest {

    @Autowired
    private OrgController orgController;

    @Autowired
    private AccountController accountController;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @Rollback
    public void shouldCreateAccount() {
        String orgExtId = "org_ext_id";
        persistOrg(orgExtId);
        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setAccountNumber("1234");
        createAccountRequest.setOwnerPhone("6789");
        createAccountRequest.setOrgExtId(orgExtId);
        GeneralResponse actualResponse = accountController.create(createAccountRequest);
        GeneralResponse expectedResponse = new GeneralResponse();
        expectedResponse.setHttpCode(200);
        assertEquals("should return success response", expectedResponse, actualResponse);

        Iterable<NeighborAccount> all = accountRepository.findAll();
        Optional<NeighborAccount> account = StreamSupport.stream(all.spliterator(), false)
                .filter(a -> createAccountRequest.getAccountNumber().equals(a.getAccountNumber()))
                .findAny();
        assertTrue("db should contain account with given urn", account.isPresent());
        NeighborAccount actualAccount = account.get();
        assertNotNull("should generate urn", actualAccount.getAccountUrn());

        String expectedUrn = orgExtId + ":" + createAccountRequest.getAccountNumber();//todo
        assertEquals("generated urn should be ", expectedUrn, actualAccount.getAccountUrn());//todo need in clarification
    }

    @Test
    @Ignore
    public void shouldCheckIfAccountNumberIsUnique() {
        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        String accountNumber = "non-unique";
        createAccountRequest.setAccountNumber(accountNumber);
        createAccountRequest.setOwnerPhone("6789");
        createAccountRequest.setOrgExtId("abc:123:vvv:bbb:nnn");
        accountController.create(createAccountRequest);
        CreateAccountRequest requestForNonUnique = new CreateAccountRequest();
        requestForNonUnique.setAccountNumber(accountNumber);
        requestForNonUnique.setOwnerPhone("6789");
        requestForNonUnique.setOrgExtId("abc:123:vvv:bbb:nnn");
        GeneralResponse actualResponse = accountController.create(requestForNonUnique);
        assertNotNull("response should contain error", actualResponse.getJsonError());
        assertEquals("should return 400 code", 400, actualResponse.getHttpCode());
        JsonError expectedError = new JsonError();
        expectedError.setCode(ErrorCode.ACCOUNT_NUMBER_IS_NOT_UNIQUE);
        assertEquals("should contain non-unique error", expectedError, actualResponse.getJsonError());
    }

    private CreateOrgRequest persistOrg(String extId) {
        CreateOrgRequest org = new CreateOrgRequest();
        org.setExtId(extId);
        org.setName("org_name");
        orgController.create(org);
        return org;
    }
}

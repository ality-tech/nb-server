package org.neighbor.api.integration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neighbor.api.ErrorCode;
import org.neighbor.api.GeneralResponse;
import org.neighbor.api.JsonError;
import org.neighbor.api.dtos.CreateOrgRequest;
import org.neighbor.controller.OrgController;
import org.neighbor.entity.*;
import org.neighbor.repository.NeighborAccountRepository;
import org.neighbor.repository.NeighborOrgRepository;
import org.neighbor.repository.NeighborRoleRepository;
import org.neighbor.repository.NeighborUserRepository;
import org.neighbor.service.NeighborAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class OrgControllerTest {

    @Autowired
    private OrgController orgController;

    @Autowired
    private NeighborOrgRepository orgRepository;
    @Autowired
    private NeighborAccountRepository accountRepository;
    @Autowired
    private NeighborAccountService accountService;
    @Autowired
    private NeighborRoleRepository roleRepository;
    @Autowired
    private NeighborUserRepository userRepository;

    @Test
    @Rollback
    /*
    /org/create
    check name and ext_id filled, and ext_id is unique
    on create also default nb_account should be created with '0' value for every fields – this account will be used for default
    operator
     */
    public void shouldCreateOrg() {//positive case
        CreateOrgRequest org = new CreateOrgRequest();
        org.setExtId("unique_id");
        org.setName("org_name");
        GeneralResponse response = orgController.create(org);

        GeneralResponse expectedRespose = new GeneralResponse(201, null);
        Assert.assertEquals("code should be 201", expectedRespose.getHttpCode(), response.getHttpCode());
        Assert.assertEquals("error should be null", expectedRespose, response);

        NeighborOrg expectedOrg = new NeighborOrg();
        expectedOrg.setExtId("unique_id");
        expectedOrg.setName("org_name");
        expectedOrg.setActive(true);
        NeighborOrg actualOrg = orgRepository.findByExtId(org.getExtId());
        expectedOrg.setId(actualOrg.getId());
        Assert.assertEquals("DB should have org", expectedOrg, actualOrg);

        NeighborAccount actualAccount = accountRepository.findDefaultByOrgId(actualOrg.getId());
        NeighborAccount expectedAccount = accountService.defaultForOrg(actualOrg.getId());
        expectedAccount.setCreatedOn(actualAccount.getCreatedOn());
        expectedAccount.setId(actualAccount.getId());
        Assert.assertEquals("DB should have default account", expectedAccount, actualAccount);

        Optional<NeighborUser> optionalUser = userRepository.findByAccountId(actualAccount.getId())
                .stream()
                .filter(user -> "0".equals(user.getLogin()))
                .findFirst();
        Assert.assertTrue("Default account should have default user", optionalUser.isPresent());

        Optional<NeighborRole> optionalRole = roleRepository.findByUserId(optionalUser.get().getId())
                .stream()
                .filter(role -> RoleEnum.NB_OPERATOR == role.getUserRole())
                .findFirst();
        Assert.assertTrue("Default user should have NB_OPERATOR role", optionalRole.isPresent());
    }

    @Test
    @Rollback
    public void shouldNotCreateWithNonUniqueExtId() {
        CreateOrgRequest org1 = new CreateOrgRequest();
        org1.setExtId("nonunique_id");
        org1.setName("org_name");
        orgController.create(org1);
        CreateOrgRequest org2 = new CreateOrgRequest();
        org2.setExtId("nonunique_id");
        org2.setName("org_name");
        GeneralResponse response = orgController.create(org2);
        Assert.assertNotNull("response should contain error", response.getJsonError());

        GeneralResponse expectedResponse = new GeneralResponse();
        JsonError error = new JsonError();
        error.setCode(ErrorCode.ORG_WITH_EXTID_ALREADY_EXISTS);
        error.setMessage("There is already exist org with given ext_id");
        error.setTimestamp(response.getJsonError().getTimestamp());
        expectedResponse.setHttpCode(400);
        expectedResponse.setJsonError(error);
        Assert.assertEquals("response should return error code 400 with specified message", expectedResponse, response);
    }

    @Test
    @Rollback
    public void shouldUpdateExistingOrg() {
        Assert.assertTrue(false);
    }

    @Test
    @Rollback
    public void shouldReturnErrorIfOrgNotExists() {
        Assert.assertTrue(false);
    }

}

package org.neighbor.api.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.neighbor.api.ErrorCode;
import org.neighbor.api.GeneralResponse;
import org.neighbor.api.JsonError;
import org.neighbor.api.dtos.*;
import org.neighbor.controller.OrgController;
import org.neighbor.entity.NeighborAccount;
import org.neighbor.entity.NeighborOrg;
import org.neighbor.repository.AccountRepository;
import org.neighbor.repository.OrgRepository;
import org.neighbor.repository.RoleRepository;
import org.neighbor.repository.UserRepository;
import org.neighbor.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class OrgControllerTest {

    public static final String NONUNIQUE_ID = "nonunique_id";
    @Autowired
    private OrgController orgController;

    @Autowired
    private OrgRepository orgRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

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
        GeneralResponse response = orgController.create(org).getBody();

        GeneralResponse expectedRespose = new GeneralResponse(201, null);
        assertEquals("code should be 201", expectedRespose.getHttpCode(), response.getHttpCode());
        assertEquals("error should be null", expectedRespose, response);

        NeighborOrg expectedOrg = new NeighborOrg();
        expectedOrg.setExtId("unique_id");
        expectedOrg.setName("org_name");
        expectedOrg.setActive(true);
        Optional<NeighborOrg> actualOrg = orgRepository.findByExtId(org.getExtId());
        assertTrue("org should exist", actualOrg.isPresent());
        expectedOrg.setId(actualOrg.get().getId());
        assertEquals("DB should have saved org", expectedOrg, actualOrg.get());
        NeighborAccount actualAccount = accountRepository.findDefaultByOrgIdAndOwnerPhone(actualOrg.get().getId(), "0");

        NeighborAccount expectedAccount = accountService.defaultForOrg(actualOrg.get().getId());
        expectedAccount.setCreatedOn(actualAccount.getCreatedOn());
        expectedAccount.setId(actualAccount.getId());
        assertEquals("DB should have default account", expectedAccount, actualAccount);

//        Optional<NeighborUser> optionalUser = userRepository.findByAccountId(actualAccount.getId())
//                .stream()
//                .filter(user -> "0".equals(user.getLogin()))
//                .findFirst();
//        assertTrue("Default account should have default user", optionalUser.isPresent());
//
//        Optional<NeighborRole> optionalRole = roleRepository.findByUserId(optionalUser.get().getId())
//                .stream()
//                .filter(role -> RoleEnum.ROLE_NB_OPERATOR == role.getUserRole())
//                .findFirst();
//        assertTrue("Default user should have ROLE_NB_OPERATOR role", optionalRole.isPresent());
    }

    @Test
    @Rollback
    public void shouldNotCreateWithNonUniqueExtId() {
        CreateOrgRequest org1 = persistOrg(NONUNIQUE_ID);
        CreateOrgRequest org2 = new CreateOrgRequest();
        org2.setExtId("nonunique_id");
        org2.setName("org_name");
        GeneralResponse response = orgController.create(org2).getBody();
        assertNotNull("response should contain error", response.getJsonError());

        GeneralResponse expectedResponse = new GeneralResponse();
        JsonError error = new JsonError();
        error.setCode(ErrorCode.ORG_WITH_EXTID_ALREADY_EXISTS);
        error.setMessage("There is already exist org with given ext_id");
        error.setTimestamp(response.getJsonError().getTimestamp());
        expectedResponse.setHttpCode(400);
        expectedResponse.setJsonError(error);
        assertEquals("response should return error code 400 with specified message", expectedResponse, response);
    }

    @Test
    @Rollback
    public void shouldUpdateExistingOrg() {
        //boiler plate
        CreateOrgRequest org1 = persistOrg("extId");

        //test
        UpdateOrgRequest updateOrgRequest = new UpdateOrgRequest();
        updateOrgRequest.setActive(false);
        updateOrgRequest.setName("updated_name");
        updateOrgRequest.setExtId(org1.getExtId());
        GeneralResponse actualResponse = orgController.update(updateOrgRequest).getBody();
        GeneralResponse expectedResponse = new GeneralResponse(200, null);
        assertEquals("should return status 200 without error", expectedResponse, actualResponse);

        Optional<NeighborOrg> foundedOrg = orgRepository.findByExtId(updateOrgRequest.getExtId());
        assertTrue("should have org", foundedOrg.isPresent());
        NeighborOrg actualOrg = foundedOrg.get();
        NeighborOrg expectedOrg = new NeighborOrg();
        expectedOrg.setExtId(org1.getExtId());
        expectedOrg.setName(updateOrgRequest.getName());
        expectedOrg.setActive(updateOrgRequest.getActive());
        expectedOrg.setId(actualOrg.getId());
        assertEquals("db should contain updated org", expectedOrg, actualOrg);
    }

    @Test
    @Rollback
    public void shouldReturnErrorIfOrgNotExists() {
        UpdateOrgRequest updateOrgRequest = new UpdateOrgRequest();
        updateOrgRequest.setExtId("non_existing_id");
        updateOrgRequest.setActive(false);
        updateOrgRequest.setName("new_name");
        GeneralResponse actualResponse = orgController.update(updateOrgRequest).getBody();
        JsonError error = new JsonError();
        error.setCode(ErrorCode.ORG_WITH_EXTID_NOT_FOUND);
        error.setMessage("There is no org with given ext_id");
        error.setTimestamp(actualResponse.getJsonError().getTimestamp());
        GeneralResponse expectedResponse = new GeneralResponse(400, error);
        assertEquals("should return error 400, with 'not found ext_id' message", expectedResponse, actualResponse);
    }

    @Test
    public void shouldDeleteOrg() {
        String extId = "org_to_delete";
        persistOrg(extId);
        DeleteOrgRequest deleteRequest = new DeleteOrgRequest();
        deleteRequest.setExtId(extId);
        GeneralResponse actualResponse = orgController.delete(deleteRequest).getBody();
        GeneralResponse expectedResponse = new GeneralResponse(200, null);
        assertEquals("should return success on delete existing", expectedResponse, actualResponse);
    }

    @Test
    public void shouldNotDeleteOrgIfAccountsExist() {
        String extId = "org_to_delete";
        persistOrg(extId);
        Optional<NeighborOrg> foundedOrg = orgRepository.findByExtId(extId);
        assertTrue("should contain org", foundedOrg.isPresent());
        NeighborOrg org = foundedOrg.get();
        NeighborAccount account = new NeighborAccount();
        account.setOrg(org);
        accountService.createAccount(account);
        DeleteOrgRequest deleteRequest = new DeleteOrgRequest();
        deleteRequest.setExtId(extId);
        GeneralResponse actualResponse = orgController.delete(deleteRequest).getBody();
        assertNotNull("response should contain jsonError", actualResponse.getJsonError());

        GeneralResponse expectedResponse = new GeneralResponse();
        JsonError error = new JsonError();
        error.setCode(ErrorCode.ORG_HAS_LINKED_ENTITIES);
        error.setMessage("There are exist non default accounts linked to given org");
        error.setTimestamp(actualResponse.getJsonError().getTimestamp());
        expectedResponse.setHttpCode(400);
        expectedResponse.setJsonError(error);

        assertEquals("shouldn't delete if there are accs appended to this org", expectedResponse, actualResponse);
    }

    @Test
    public void shouldReturnListOfOrgs() {
        List<OrgDto> actualResult = orgController.list().getBody();
        int beforeSize = actualResult.size();
        CreateOrgRequest org = new CreateOrgRequest();
        org.setExtId("unique_id");
        org.setName("org_name");
        orgController.create(org);
        actualResult = orgController.list().getBody();
        assertEquals("should return list with +1 org", beforeSize+1, actualResult.size());
        OrgDto dto = new OrgDto();
        dto.setActive(true);
        dto.setName(org.getName());
        dto.setExtId(org.getExtId());
        assertTrue("returned list should contain saved org", actualResult.contains(dto));
    }

    @Test
    public void shouldReturnOrgById() {
        String uniqueId = "uniqueId";
        persistOrg(uniqueId);
        GetOrgByIdRequest request = new GetOrgByIdRequest();
        request.setId(uniqueId);
        OrgDto orgDto = orgController.getById(request).getBody();
        assertNotNull("should found org", orgDto);
        GetOrgByIdRequest request2 = new GetOrgByIdRequest();
        request.setId("non exist id");
        OrgDto orgDto2 = orgController.getById(request).getBody();
        assertNull("should return exception", orgDto2);
    }

    private CreateOrgRequest persistOrg(String extId) {
        CreateOrgRequest org = new CreateOrgRequest();
        org.setExtId(extId);
        org.setName("org_name");
        orgController.create(org);
        return org;
    }
}

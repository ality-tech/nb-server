package org.neighbor.api.integration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neighbor.api.ErrorCode;
import org.neighbor.api.GeneralResponse;
import org.neighbor.api.JsonError;
import org.neighbor.api.dtos.CreateOrgRequest;
import org.neighbor.controller.OrgController;
import org.neighbor.entity.NeighborAccount;
import org.neighbor.entity.NeighborOrg;
import org.neighbor.repository.NeighborAccountRepository;
import org.neighbor.repository.NeighborOrgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class OrgControllerTest {

    @Autowired
    private OrgController orgController;

    @Autowired
    private NeighborOrgRepository neighborOrgRepository;
    @Autowired
    private NeighborAccountRepository neighborAccountRepository;

    @Test
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
        NeighborOrg actualOrg = neighborOrgRepository.findByExtId(org.getExtId());
        expectedOrg.setId(actualOrg.getId());
        Assert.assertEquals("DB should have org", expectedOrg, actualOrg);

        NeighborAccount actualAccount = neighborAccountRepository.findDefaultByOrgId(actualOrg.getId());
        NeighborAccount expectedAccount = defaultForOrg(actualOrg.getId());
        Assert.assertEquals("DB should have default account", expectedAccount, actualAccount);
    }

    @Test
    public void shouldNotCreateWithNonUniqueExtId() {
        CreateOrgRequest org1 = new CreateOrgRequest();
        org1.setExtId("unique_id");
        org1.setName("org_name");
        orgController.create(org1);
        CreateOrgRequest org2 = new CreateOrgRequest();
        org2.setExtId("unique_id");
        org2.setName("org_name");
        GeneralResponse response = orgController.create(org2);

        GeneralResponse expectedResponse = new GeneralResponse();
        JsonError error = new JsonError();
        error.setCode(ErrorCode.ORG_WITH_EXTID_ALREADY_EXISTS);
        error.setMessage("There is already exist org with given ext_id");
        expectedResponse.setHttpCode(400);
        expectedResponse.setJsonError(error);

        Assert.assertEquals("response should return error", expectedResponse, response);
    }

    private NeighborAccount defaultForOrg(Long orgId){
        NeighborAccount account = new NeighborAccount();
        account.setOrgId(orgId);
        account.setActive(true);
        account.setAccountNumber("0");
        account.setAddressBuilding("0");
        account.setAddressFlat("0");
        account.setAddressFloor("0");
        account.setAddressStreet("0");
        account.setCreatedOn(new Date());
        account.setOwnerPhone("0");
//        account.setAccountUrn("");  todo
        return account;
    }
}

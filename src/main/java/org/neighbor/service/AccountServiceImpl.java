package org.neighbor.service;

import org.neighbor.api.GeneralResponse;
import org.neighbor.api.dtos.CreateAccountRequest;
import org.neighbor.entity.NeighborAccount;
import org.neighbor.entity.NeighborOrg;
import org.neighbor.mappers.CreateAccountRequestToAccount;
import org.neighbor.repository.AccountRepository;
import org.neighbor.repository.OrgRepository;
import org.neighbor.utils.ResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OrgRepository orgRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CreateAccountRequestToAccount requestToAccountMapper;

    @Override
    public NeighborAccount createAccount(NeighborAccount account) {
        return accountRepository.save(account);
    }

    @Override
    public GeneralResponse createAccount(CreateAccountRequest createAccountRequest) {
        Optional<NeighborOrg> foundOrg = orgRepository.findByExtId(createAccountRequest.getOrgExtId());
        if (!foundOrg.isPresent())
            return ResponseGenerator.ORG_NOTEXIST_ERROR;
        NeighborOrg org = foundOrg.get();
        accountRepository.findDefaultByOrgIdAndAccountNumber(org.getId(), createAccountRequest.getAccountNumber());
        NeighborAccount account = requestToAccountMapper.createAccountRequestToAccount(createAccountRequest);
        String urn = createAccountRequest.getOrgExtId() + ":" + createAccountRequest.getAccountNumber();//todo
        account.setAccountUrn(urn);
        account.setOrg(org);
        account.setCreatedOn(new Date());
        accountRepository.save(account);
        return generateOkResponse();
    }

    @Override
    public void createDefaultAccountForOrgId(Long orgId) {
        NeighborAccount account = createAccount(defaultForOrg(orgId));
//        userService.createDefaultUserForAccountId(account.getId());
    }

    @Override
    public Iterable<NeighborAccount> listByFilter(Object filter) {
        //// FIXME
        return accountRepository.findAll();
    }

    @Override
    public List<NeighborAccount> findByOrg(NeighborOrg byExtId) {
        return accountRepository.findByOrgId(byExtId.getId());
    }

    @Override
    public NeighborAccount findDefaultByOrgIdAndOwnerPhone(Long orgId, String ownerPhone) {
        return accountRepository.findDefaultByOrgIdAndOwnerPhone(orgId, ownerPhone);
    }

    @Override
    public void delete(NeighborAccount account) {
        accountRepository.delete(account);
    }

    @Override
    public NeighborAccount defaultForOrg(Long orgId) {
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
//        account.setOrgExtId("");  todo
        return account;
    }

    private GeneralResponse generateOkResponse() {
        return new GeneralResponse(200, null);
    }

}

package org.neighbor.server.service.impl;

import org.neighbor.api.GeneralResponse;
import org.neighbor.api.dtos.CreateAccountRequest;
import org.neighbor.server.entity.NeighborAccountEntity;
import org.neighbor.server.entity.NeighborOrgEntity;
import org.neighbor.server.mappers.CreateAccountRequestToAccount;
import org.neighbor.server.repository.AccountRepository;
import org.neighbor.server.repository.OrgRepository;
import org.neighbor.server.service.AccountService;
import org.neighbor.server.service.UserService;
import org.neighbor.server.utils.ResponseGenerator;
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
    public NeighborAccountEntity createAccount(NeighborAccountEntity account) {
        return accountRepository.save(account);
    }

    @Override
    public GeneralResponse createAccount(CreateAccountRequest createAccountRequest) {
        Optional<NeighborOrgEntity> foundOrg = orgRepository.findByExtId(createAccountRequest.getOrgExtId());
        if (!foundOrg.isPresent())
            return ResponseGenerator.generateOrgNotExistError();
        NeighborOrgEntity org = foundOrg.get();
        accountRepository.findDefaultByOrgIdAndAccountNumber(org.getId(), createAccountRequest.getAccountNumber());
        NeighborAccountEntity account = requestToAccountMapper.createAccountRequestToAccount(createAccountRequest);
        String urn = createAccountRequest.getOrgExtId() + ":" + createAccountRequest.getAccountNumber();//todo
        account.setAccountUrn(urn);
        account.setOrg(org);
        account.setCreatedOn(new Date());
        accountRepository.save(account);
        return generateOkResponse(201);
    }

    @Override
    public void createDefaultAccountForOrgId(NeighborOrgEntity org) {
        NeighborAccountEntity account = createAccount(defaultForOrg(org));

//        userService.createDefaultUserForAccountId(account.getId());
    }

    @Override
    public Iterable<NeighborAccountEntity> listByFilter(Object filter) {
        //// FIXME
        return accountRepository.findAll();
    }

    @Override
    public List<NeighborAccountEntity> findByOrg(NeighborOrgEntity byExtId) {
        return accountRepository.findByOrgId(byExtId.getId());
    }

    @Override
    public NeighborAccountEntity findDefaultByOrgIdAndOwnerPhone(Long orgId, String ownerPhone) {
        return accountRepository.findDefaultByOrgIdAndOwnerPhone(orgId, ownerPhone);
    }

    @Override
    public void delete(NeighborAccountEntity account) {
        accountRepository.delete(account);
    }

    @Override
    public NeighborAccountEntity defaultForOrg(NeighborOrgEntity org) {
        NeighborAccountEntity account = new NeighborAccountEntity();
        account.setOrg(org);
        account.setActive(true);
        account.setAccountNumber("0");
        account.setAddressBuilding("0");
        account.setAddressFlat("0");
        account.setAddressFloor("0");
        account.setAddressStreet("0");
        account.setCreatedOn(new Date());
        account.setOwnerPhone("0");
        account.setAccountUrn(org.getExtId() + ":" + account.getAccountNumber());
        return account;
    }

    @Override
    public Optional<NeighborAccountEntity> findById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Optional<NeighborAccountEntity> findOrgAndAccountNumber(Long orgId, String accountNumber) {
        return accountRepository.findByOrgIdAndAccountNumber(orgId, accountNumber);
    }

    private GeneralResponse generateOkResponse(int code) {
        return new GeneralResponse(code, null);
    }

}

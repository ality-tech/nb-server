package org.neighbor.server.service.impl;

import org.neighbor.api.GeneralResponse;
import org.neighbor.api.account.CreateAccountRequest;
import org.neighbor.server.entity.AccountEntity;
import org.neighbor.server.entity.OrgEntity;
import org.neighbor.server.mapper.AccountMapper;
import org.neighbor.server.repository.AccountRepository;
import org.neighbor.server.repository.OrgRepository;
import org.neighbor.server.service.AccountService;
import org.neighbor.server.service.UserService;
import org.neighbor.server.util.ResponseGenerator;
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
    private AccountMapper accountMapper;

    @Override
    public AccountEntity createAccount(AccountEntity account) {
        return accountRepository.save(account);
    }

    @Override
    public GeneralResponse createAccount(CreateAccountRequest createAccountRequest) {
        Optional<OrgEntity> foundOrg = orgRepository.findByExtId(createAccountRequest.getOrgExtId());
        if (!foundOrg.isPresent())
            return ResponseGenerator.generateOrgNotExistError();
        OrgEntity org = foundOrg.get();
        accountRepository.findDefaultByOrgIdAndAccountNumber(org.getId(), createAccountRequest.getAccountNumber());
        AccountEntity account = accountMapper.createAccountRequestToAccount(createAccountRequest);
        String urn = createAccountRequest.getOrgExtId() + ":" + createAccountRequest.getAccountNumber();//todo
        account.setAccountUrn(urn);
        account.setOrg(org);
        account.setCreatedOn(new Date());
        accountRepository.save(account);
        return generateOkResponse(201);
    }

    @Override
    public void createDefaultAccountForOrgId(OrgEntity org) {
        AccountEntity account = createAccount(defaultForOrg(org));

//        userService.createDefaultUserForAccountId(account.getId());
    }

    @Override
    public Iterable<AccountEntity> listByFilter(Object filter) {
        //// FIXME
        return accountRepository.findAll();
    }

    @Override
    public List<AccountEntity> findByOrg(OrgEntity byExtId) {
        return accountRepository.findByOrgId(byExtId.getId());
    }

    @Override
    public AccountEntity findDefaultByOrgIdAndOwnerPhone(Long orgId, String ownerPhone) {
        return accountRepository.findDefaultByOrgIdAndOwnerPhone(orgId, ownerPhone);
    }

    @Override
    public void delete(AccountEntity account) {
        accountRepository.delete(account);
    }

    @Override
    public AccountEntity defaultForOrg(OrgEntity org) {
        AccountEntity account = new AccountEntity();
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
    public Optional<AccountEntity> findById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Optional<AccountEntity> findOrgAndAccountNumber(Long orgId, String accountNumber) {
        return accountRepository.findByOrgIdAndAccountNumber(orgId, accountNumber);
    }

    private GeneralResponse generateOkResponse(int code) {
        return new GeneralResponse(code, null);
    }

}

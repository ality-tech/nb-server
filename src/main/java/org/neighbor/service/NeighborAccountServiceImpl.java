package org.neighbor.service;

import org.neighbor.entity.NeighborAccount;
import org.neighbor.repository.NeighborAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class NeighborAccountServiceImpl implements NeighborAccountService {

    @Autowired
    private NeighborAccountRepository neighborAccountRepository;

    @Autowired
    private NeighborUserService userService;

    @Override
    public NeighborAccount createAccount(NeighborAccount account) {
        return neighborAccountRepository.save(account);
    }

    @Override
    public void createDefaultAccountForOrgId(Long orgId) {
        NeighborAccount account = createAccount(defaultForOrg(orgId));
        userService.createDefaultUserForAccountId(account.getId());
    }

    @Override
    public Iterable<NeighborAccount> listByFilter(Object filter) {
        return neighborAccountRepository.findAll();
    }

    @Override
    public NeighborAccount defaultForOrg(Long orgId){
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

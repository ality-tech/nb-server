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

    @Override
    public void createAccount(NeighborAccount account) {
        neighborAccountRepository.save(account);
    }

    @Override
    public void createDefaultAccountForExtId(Long id) {
        createAccount(defaultForOrg(id));
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

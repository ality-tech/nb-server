package org.neighbor.service;

import org.neighbor.api.GeneralResponse;
import org.neighbor.api.dtos.CreateAccountRequest;
import org.neighbor.entity.NeighborAccount;
import org.neighbor.entity.NeighborOrg;

import java.util.List;

public interface AccountService {

    NeighborAccount createAccount(NeighborAccount account);
    GeneralResponse createAccount(CreateAccountRequest createAccountRequest);

    void createDefaultAccountForOrgId(Long id);

    NeighborAccount defaultForOrg(Long orgId);

    List<NeighborAccount> findByOrg(NeighborOrg byExtId);

    Iterable<NeighborAccount> listByFilter(Object filter);

    void delete(NeighborAccount account);

    NeighborAccount findDefaultByOrgIdAndOwnerPhone(Long orgId, String ownerPhone);
}

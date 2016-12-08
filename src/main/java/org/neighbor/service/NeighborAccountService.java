package org.neighbor.service;

import org.neighbor.entity.NeighborAccount;

public interface NeighborAccountService {

    NeighborAccount createAccount(NeighborAccount account);

    void createDefaultAccountForOrgId(Long id);

    NeighborAccount defaultForOrg(Long orgId);

    Iterable<NeighborAccount> listByFilter(Object filter);
}

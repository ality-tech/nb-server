package org.neighbor.service;

import org.neighbor.entity.NeighborAccount;

public interface NeighborAccountService {

    void createAccount(NeighborAccount account);

    void createDefaultAccountForExtId(Long id);

    NeighborAccount defaultForOrg(Long orgId);
}

package org.neighbor.service;

import org.neighbor.api.GeneralResponse;
import org.neighbor.api.dtos.CreateAccountRequest;
import org.neighbor.entity.NeighborAccount;
import org.neighbor.entity.NeighborOrg;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    NeighborAccount createAccount(NeighborAccount account);

    GeneralResponse createAccount(CreateAccountRequest createAccountRequest);

    void createDefaultAccountForOrgId(NeighborOrg id);

    NeighborAccount defaultForOrg(NeighborOrg orgId);

    List<NeighborAccount> findByOrg(NeighborOrg byExtId);

    Iterable<NeighborAccount> listByFilter(Object filter);

    void delete(NeighborAccount account);

    NeighborAccount findDefaultByOrgIdAndOwnerPhone(Long orgId, String ownerPhone);

    Optional<NeighborAccount> findById(Long accountId);

    Optional<NeighborAccount> findOrgAndAccountNumber(Long orgId, String accountNumber);
}

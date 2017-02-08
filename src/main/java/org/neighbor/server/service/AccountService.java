package org.neighbor.server.service;

import org.neighbor.api.GeneralResponse;
import org.neighbor.api.dtos.CreateAccountRequest;
import org.neighbor.server.entity.NeighborAccountEntity;
import org.neighbor.server.entity.NeighborOrgEntity;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    NeighborAccountEntity createAccount(NeighborAccountEntity account);

    GeneralResponse createAccount(CreateAccountRequest createAccountRequest);

    void createDefaultAccountForOrgId(NeighborOrgEntity id);

    NeighborAccountEntity defaultForOrg(NeighborOrgEntity orgId);

    List<NeighborAccountEntity> findByOrg(NeighborOrgEntity byExtId);

    Iterable<NeighborAccountEntity> listByFilter(Object filter);

    void delete(NeighborAccountEntity account);

    NeighborAccountEntity findDefaultByOrgIdAndOwnerPhone(Long orgId, String ownerPhone);

    Optional<NeighborAccountEntity> findById(Long accountId);

    Optional<NeighborAccountEntity> findOrgAndAccountNumber(Long orgId, String accountNumber);
}

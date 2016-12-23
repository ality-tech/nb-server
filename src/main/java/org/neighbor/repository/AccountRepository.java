package org.neighbor.repository;

import org.neighbor.entity.NeighborAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface AccountRepository extends CrudRepository<NeighborAccount, Long>{
    //// FIXME: should be DEFAULT acc
    NeighborAccount findDefaultByOrgIdAndOwnerPhone(Long orgId, String ownerPhone);

    List<NeighborAccount> findByOrgId(Long orgId);

    List<NeighborAccount> findDefaultByOrgIdAndAccountNumber(Long orgId, String accountNumber);

    Optional<NeighborAccount> findByOrgIdAndAccountNumber(Long orgId, String accountNumber);

    Optional<NeighborAccount> findById(Long accountId);
}

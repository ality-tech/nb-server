package org.neighbor.server.repository;

import org.neighbor.server.entity.NeighborAccountEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface AccountRepository extends CrudRepository<NeighborAccountEntity, Long>{
    //// FIXME: should be DEFAULT acc
    NeighborAccountEntity findDefaultByOrgIdAndOwnerPhone(Long orgId, String ownerPhone);

    List<NeighborAccountEntity> findByOrgId(Long orgId);

    List<NeighborAccountEntity> findDefaultByOrgIdAndAccountNumber(Long orgId, String accountNumber);

    Optional<NeighborAccountEntity> findByOrgIdAndAccountNumber(Long orgId, String accountNumber);

    Optional<NeighborAccountEntity> findById(Long accountId);
}

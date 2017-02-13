package org.neighbor.server.repository;

import org.neighbor.server.entity.AccountEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface AccountRepository extends CrudRepository<AccountEntity, Long>{
    //// FIXME: should be DEFAULT acc
    AccountEntity findDefaultByOrgIdAndOwnerPhone(Long orgId, String ownerPhone);

    List<AccountEntity> findByOrgId(Long orgId);

    List<AccountEntity> findDefaultByOrgIdAndAccountNumber(Long orgId, String accountNumber);

    Optional<AccountEntity> findByOrgIdAndAccountNumber(Long orgId, String accountNumber);

    Optional<AccountEntity> findById(Long accountId);
}

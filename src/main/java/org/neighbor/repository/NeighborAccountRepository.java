package org.neighbor.repository;

import org.neighbor.entity.NeighborAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface NeighborAccountRepository extends CrudRepository<NeighborAccount, Long>{
    //// FIXME: should be DEFAULT acc
    NeighborAccount findDefaultForOrg(@Param("orgId")Long orgId);
}

package org.neighbor.repository;

import org.neighbor.entity.NeighborOrg;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface NeighborOrgRepository extends CrudRepository<NeighborOrg, Long>{

    NeighborOrg findByExtId(@Param("extId") String extId);


}

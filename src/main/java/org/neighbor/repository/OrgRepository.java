package org.neighbor.repository;

import org.neighbor.entity.NeighborOrg;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface OrgRepository extends CrudRepository<NeighborOrg, Long>{

    Optional<NeighborOrg> findByExtId(@Param("extId") String extId);
}

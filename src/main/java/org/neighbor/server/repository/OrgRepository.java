package org.neighbor.server.repository;

import org.neighbor.server.entity.NeighborOrgEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface OrgRepository extends CrudRepository<NeighborOrgEntity, Long>{

    Optional<NeighborOrgEntity> findByExtId(@Param("extId") String extId);

}

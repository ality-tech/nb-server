package org.neighbor.server.repository;

import org.neighbor.server.entity.OrgEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface OrgRepository extends CrudRepository<OrgEntity, Long>{

    Optional<OrgEntity> findByExtId(@Param("extId") String extId);

}

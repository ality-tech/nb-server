package org.neighbor.server.repository;

import org.neighbor.server.entity.JobTypeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JobTypeRepository extends CrudRepository<JobTypeEntity, Long> {

    JobTypeEntity findByExtId(String id);

    List<JobTypeEntity> findByOrgExtId(String id);
}

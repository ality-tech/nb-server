package org.neighbor.server.repository;

import org.neighbor.server.entity.JobCategoryEntity;
import org.neighbor.server.entity.JobTypeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JobCategoryRepository extends CrudRepository<JobCategoryEntity, Long> {

    List<JobCategoryEntity> findByType(JobTypeEntity value);

    JobCategoryEntity findByExtId(String extId);
}

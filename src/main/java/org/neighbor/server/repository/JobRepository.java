package org.neighbor.server.repository;

import org.neighbor.server.entity.JobEntity;
import org.neighbor.server.entity.JobCategoryEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface JobRepository extends CrudRepository<JobEntity, Long> {

    List<JobEntity> findByCategory(JobCategoryEntity value);

    JobEntity findByJobUrn(String jobUrn);
}

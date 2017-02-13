package org.neighbor.server.repository;

import org.neighbor.server.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface RoleRepository extends CrudRepository<RoleEntity, Long>{

    List<RoleEntity> findByUserId(@Param("user_id") Long usrId);

}

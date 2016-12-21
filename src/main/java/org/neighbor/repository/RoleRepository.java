package org.neighbor.repository;

import org.neighbor.entity.NeighborRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface RoleRepository extends CrudRepository<NeighborRole, Long>{

    List<NeighborRole> findByUserId(@Param("user_id") Long usrId);

}

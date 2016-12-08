package org.neighbor.repository;

import org.neighbor.entity.NeighborUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface NeighborUserRepository extends CrudRepository<NeighborUser, Long> {

    NeighborUser findByLogin(@Param("login") String login);

}

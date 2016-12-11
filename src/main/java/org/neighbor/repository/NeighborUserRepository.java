package org.neighbor.repository;

import org.neighbor.entity.NeighborUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface NeighborUserRepository extends CrudRepository<NeighborUser, Long> {

    NeighborUser findByLogin(@Param("login") String login);

}

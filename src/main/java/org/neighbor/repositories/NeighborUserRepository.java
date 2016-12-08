package org.neighbor.repositories;

import org.neighbor.entity.NeighborUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "neighbor", path = "neighbor")
public interface NeighborUserRepository extends CrudRepository<NeighborUser, Long> {

    List<NeighborUser> findByLogin(@Param("login") String login);

}

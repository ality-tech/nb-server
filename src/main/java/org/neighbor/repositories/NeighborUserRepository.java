package org.neighbor.repositories;

import org.neighbor.entity.NeighborUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

//@RepositoryRestResource(collectionResourceRel = "neighbor", path = "user")
public interface NeighborUserRepository extends CrudRepository<NeighborUser, Long> {

    List<NeighborUser> findByLogin(@Param("login") String login);

}

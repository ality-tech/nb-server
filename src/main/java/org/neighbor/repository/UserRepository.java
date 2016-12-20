package org.neighbor.repository;

import org.neighbor.entity.NeighborUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserRepository extends CrudRepository<NeighborUser, Long> {

    NeighborUser findByLogin(@Param("login") String login);

    List<NeighborUser> findByAccountId(@Param("account_id")Long accountId);
}

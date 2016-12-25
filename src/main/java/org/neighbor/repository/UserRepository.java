package org.neighbor.repository;

import org.neighbor.entity.NeighborUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface UserRepository extends CrudRepository<NeighborUser, Long> {

    Optional<NeighborUser> findByLogin(@Param("login") String login);

    @Query("")
    Optional<NeighborUser> findActiveByLogin(@Param("login") String login);

    List<NeighborUser> findByAccountId(@Param("account_id")Long accountId);

    Optional<NeighborUser> findUserByLoginAndPinCode(@Param("login") String login, @Param("pinCode") String pinCode);
}

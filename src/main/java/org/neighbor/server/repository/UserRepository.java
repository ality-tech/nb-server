package org.neighbor.server.repository;

import org.neighbor.server.entity.NeighborUserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface UserRepository extends CrudRepository<NeighborUserEntity, Long> {

    Optional<NeighborUserEntity> findByLogin(@Param("login") String login);

    @Query("")
    Optional<NeighborUserEntity> findActiveByLogin(@Param("login") String login);

    List<NeighborUserEntity> findByAccountId(@Param("account_id")Long accountId);

    Optional<NeighborUserEntity> findUserByLoginAndPinCode(@Param("login") String login, @Param("pinCode") String pinCode);
}

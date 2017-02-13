package org.neighbor.server.repository;

import org.neighbor.server.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByLogin(@Param("login") String login);

    @Query("")
    Optional<UserEntity> findActiveByLogin(@Param("login") String login);

    List<UserEntity> findByAccountId(@Param("account_id")Long accountId);

    Optional<UserEntity> findUserByLoginAndPinCode(@Param("login") String login, @Param("pinCode") String pinCode);
}

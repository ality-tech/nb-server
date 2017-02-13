package org.neighbor.server.repository;

import org.neighbor.server.entity.ActivationTokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface TokenRepository extends CrudRepository<ActivationTokenEntity, Long> {
    List<ActivationTokenEntity> findByUserId(Long userId);

    Optional<ActivationTokenEntity> findByToken(String token);
}

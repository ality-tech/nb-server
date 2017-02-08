package org.neighbor.server.repository;

import org.neighbor.server.entity.NeighborActivationTokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface TokenRepository extends CrudRepository<NeighborActivationTokenEntity, Long> {
    List<NeighborActivationTokenEntity> findByUserId(Long userId);

    Optional<NeighborActivationTokenEntity> findByToken(String token);
}

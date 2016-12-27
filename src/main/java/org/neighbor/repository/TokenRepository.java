package org.neighbor.repository;

import org.neighbor.entity.NeighborActivationToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface TokenRepository extends CrudRepository<NeighborActivationToken, Long> {
    List<NeighborActivationToken> findByUserId(Long userId);

    Optional<NeighborActivationToken> findByToken(String token);
}

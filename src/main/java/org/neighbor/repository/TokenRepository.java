package org.neighbor.repository;

import org.neighbor.entity.NeighborActivationToken;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TokenRepository extends CrudRepository<NeighborActivationToken, Long> {
    List<NeighborActivationToken> findByUserId(Long userId);
}

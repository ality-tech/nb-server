package org.neighbor.server.repository;

import org.neighbor.server.entity.NeighborUserStatusHistoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserHistoryRepository extends CrudRepository<NeighborUserStatusHistoryEntity, Long> {

    List<NeighborUserStatusHistoryEntity> findByUsrId(Long usrId);

}

package org.neighbor.repository;

import org.neighbor.entity.NeighborUserStatusHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserHistoryRepository extends CrudRepository<NeighborUserStatusHistory, Long> {

    List<NeighborUserStatusHistory> findByUsrId(Long usrId);

}

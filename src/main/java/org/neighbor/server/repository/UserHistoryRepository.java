package org.neighbor.server.repository;

import org.neighbor.server.entity.UserStatusHistoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserHistoryRepository extends CrudRepository<UserStatusHistoryEntity, Long> {

    List<UserStatusHistoryEntity> findByUsrId(Long usrId);

}

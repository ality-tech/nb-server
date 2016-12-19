package org.neighbor.service;

import org.neighbor.entity.NeighborUser;

public interface NeighborUserService {
    NeighborUser createDefaultUserForAccountId(Long accountId);

    NeighborUser defaultForAccount(Long accountId);
}

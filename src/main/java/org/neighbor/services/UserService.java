package org.neighbor.services;

import org.neighbor.entity.NeighborUser;

import java.util.List;

public interface UserService {
    NeighborUser getUserByUrn(String urn);

    //todo add Filter
    List<NeighborUser> getUsersByFilter(Object filter);

    //todo mapping!
    boolean updateUser(NeighborUser user);
}

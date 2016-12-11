package org.neighbor.service;

import org.neighbor.api.dtos.UserDto;
import org.neighbor.entity.NeighborUser;

import java.util.List;

public interface UserService {

    UserDto getUserByUrn(String urn);

    //todo add Filter
    List<UserDto> getUsersByFilter(Object filter);

    boolean updateUser(NeighborUser user);
}

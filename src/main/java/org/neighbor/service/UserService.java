package org.neighbor.service;

import org.neighbor.api.AccountUrn;
import org.neighbor.api.GeneralResponse;
import org.neighbor.api.dtos.CreateUserRequest;
import org.neighbor.api.dtos.UserDto;
import org.neighbor.entity.NeighborUser;

import java.util.List;
import java.util.Optional;

public interface UserService {

//    NeighborUser createDefaultUserForAccountId(Long accountId);

//    NeighborUser defaultForAccount(Long accountId);

    UserDto getUserByUrn(String urn);

    //todo add Filter
    List<UserDto> getUsersByFilter(Object filter);

    boolean updateUser(NeighborUser user);

    GeneralResponse create(CreateUserRequest request, AccountUrn accountUrn);

    Optional<NeighborUser> getUserByLoginAndPin(String login, String pin);

    Optional<NeighborUser> getUserByLogin(String login);
}

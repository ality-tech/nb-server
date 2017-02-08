package org.neighbor.server.service;

import org.neighbor.api.AccountUrn;
import org.neighbor.api.GeneralResponse;
import org.neighbor.api.dtos.CreateUserRequest;
import org.neighbor.api.dtos.UserDto;
import org.neighbor.server.entity.NeighborUserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

//    NeighborUser createDefaultUserForAccountId(Long accountId);

//    NeighborUser defaultForAccount(Long accountId);

    UserDto getUserByUrn(String urn);

    //todo add Filter
    List<UserDto> getUsersByFilter(Object filter);

    boolean updateUser(NeighborUserEntity user);

    GeneralResponse create(CreateUserRequest request, AccountUrn accountUrn);

    Optional<NeighborUserEntity> getUserByLoginAndPin(String login, String pin);

    Optional<NeighborUserEntity> getUserByLogin(String login);
}

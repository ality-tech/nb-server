package org.neighbor.server.service;

import org.neighbor.api.account.AccountUrn;
import org.neighbor.api.GeneralResponse;
import org.neighbor.api.user.CreateUserRequest;
import org.neighbor.api.user.UserDto;
import org.neighbor.server.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

//    NeighborUser createDefaultUserForAccountId(Long accountId);

//    NeighborUser defaultForAccount(Long accountId);

    UserDto getUserByUrn(String urn);

    //todo add Filter
    List<UserDto> getUsersByFilter(Object filter);

    boolean updateUser(UserEntity user);

    GeneralResponse create(CreateUserRequest request, AccountUrn accountUrn);

    Optional<UserEntity> getUserByLoginAndPin(String login, String pin);

    Optional<UserEntity> getUserByLogin(String login);
}

package org.neighbor.server.mapper;

import org.mapstruct.Mapper;
import org.neighbor.api.user.UserDto;
import org.neighbor.server.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
    //todo urn -> id
    UserDto userToUserDto(UserEntity user);
}

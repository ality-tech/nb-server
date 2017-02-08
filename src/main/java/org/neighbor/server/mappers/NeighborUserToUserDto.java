package org.neighbor.server.mappers;

import org.mapstruct.Mapper;
import org.neighbor.api.dtos.UserDto;
import org.neighbor.server.entity.NeighborUserEntity;

@Mapper(componentModel = "spring")
public interface NeighborUserToUserDto {
    //todo urn -> id
    UserDto userToUserDto(NeighborUserEntity user);
}

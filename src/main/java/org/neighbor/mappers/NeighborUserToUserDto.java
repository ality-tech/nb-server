package org.neighbor.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.neighbor.api.dtos.UserDto;
import org.neighbor.entity.NeighborUser;

@Mapper(componentModel = "spring")
public interface NeighborUserToUserDto {
    //todo urn -> id
    UserDto userToUserDto(NeighborUser user);
}

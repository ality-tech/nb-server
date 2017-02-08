package org.neighbor.server.mappers;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.neighbor.api.dtos.OrgDto;
import org.neighbor.server.entity.NeighborOrgEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NeighborOrgToOrgDto {

    @IterableMapping(qualifiedByName = "orgToOrgDto")
    List<OrgDto> iterableOrgsToListOrgDtos(Iterable<NeighborOrgEntity> neighborOrg);

    @Named("orgToOrgDto")
    OrgDto orgToOrgDto(NeighborOrgEntity org);

}

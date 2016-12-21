package org.neighbor.mappers;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.neighbor.api.dtos.OrgDto;
import org.neighbor.entity.NeighborOrg;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NeighborOrgToOrgDto {

    @IterableMapping(qualifiedByName = "orgToOrgDto")
    List<OrgDto> iterableOrgsToListOrgDtos(Iterable<NeighborOrg> neighborOrg);

    @Named("orgToOrgDto")
    OrgDto orgToOrgDto(NeighborOrg org);

}

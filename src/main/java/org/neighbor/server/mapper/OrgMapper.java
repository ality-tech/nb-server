package org.neighbor.server.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.neighbor.api.organizaton.OrgDto;
import org.neighbor.server.entity.OrgEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrgMapper {

    @IterableMapping(qualifiedByName = "orgToOrgDto")
    List<OrgDto> iterableOrgsToListOrgDtos(Iterable<OrgEntity> neighborOrg);

    @Named("orgToOrgDto")
    OrgDto orgToOrgDto(OrgEntity org);

}

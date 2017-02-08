package org.neighbor.server.mappers;

import org.mapstruct.Mapper;
import org.neighbor.api.dtos.CreateAccountRequest;
import org.neighbor.server.entity.NeighborAccountEntity;

@Mapper(componentModel = "spring")
public interface CreateAccountRequestToAccount {

    NeighborAccountEntity createAccountRequestToAccount(CreateAccountRequest request);

}

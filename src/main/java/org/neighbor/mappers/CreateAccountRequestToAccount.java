package org.neighbor.mappers;

import org.mapstruct.Mapper;
import org.neighbor.api.dtos.CreateAccountRequest;
import org.neighbor.entity.NeighborAccount;

@Mapper(componentModel = "spring")
public interface CreateAccountRequestToAccount {

    NeighborAccount createAccountRequestToAccount(CreateAccountRequest request);

}

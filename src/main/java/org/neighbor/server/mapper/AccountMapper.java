package org.neighbor.server.mapper;

import org.mapstruct.Mapper;
import org.neighbor.api.account.CreateAccountRequest;
import org.neighbor.server.entity.AccountEntity;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountEntity createAccountRequestToAccount(CreateAccountRequest request);

}

package org.neighbor.server.service;

import org.neighbor.api.GeneralResponse;
import org.neighbor.api.account.CreateAccountRequest;
import org.neighbor.server.entity.AccountEntity;
import org.neighbor.server.entity.OrgEntity;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    AccountEntity createAccount(AccountEntity account);

    GeneralResponse createAccount(CreateAccountRequest createAccountRequest);

    void createDefaultAccountForOrgId(OrgEntity id);

    AccountEntity defaultForOrg(OrgEntity orgId);

    List<AccountEntity> findByOrg(OrgEntity byExtId);

    Iterable<AccountEntity> listByFilter(Object filter);

    void delete(AccountEntity account);

    AccountEntity findDefaultByOrgIdAndOwnerPhone(Long orgId, String ownerPhone);

    Optional<AccountEntity> findById(Long accountId);

    Optional<AccountEntity> findOrgAndAccountNumber(Long orgId, String accountNumber);
}

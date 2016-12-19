package org.neighbor.service;

import org.neighbor.entity.ActivationStatus;
import org.neighbor.entity.NeighborRole;
import org.neighbor.entity.NeighborUser;
import org.neighbor.repository.NeighborUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class NeighborUserServiceImpl implements NeighborUserService {
    @Autowired
    private NeighborUserRepository userRepository;

    @Autowired
    private NeighborRoleService roleService;

    @Override
    public NeighborUser createDefaultUserForAccountId(Long accountId) {
        NeighborUser user = userRepository.save(defaultForAccount(accountId));
        roleService.createDefaultRoleForUserId(user.getId());
        return user;
    }

    @Override
    public NeighborUser defaultForAccount(Long accountId) {
        NeighborUser user = new NeighborUser();
        user.setUserPhone("0");
        user.setPinCode("0");//todo ???
        user.setCreatedOn(new Date());
        user.setAccountId(accountId);
        user.setActivationStatus(ActivationStatus.ACTIVE);
        user.setUserUrn("0");
        user.setLogin("0");//todo ???
        return user;
    }


}

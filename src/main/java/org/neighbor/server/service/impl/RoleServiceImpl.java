package org.neighbor.server.service.impl;

import org.neighbor.server.entity.NeighborRoleEntity;
import org.neighbor.api.RoleEnum;
import org.neighbor.server.repository.RoleRepository;
import org.neighbor.server.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void createDefaultRoleForUserId(Long userId) {
        NeighborRoleEntity role = roleRepository.save(defaultRoleForUserId(userId));
    }

    private NeighborRoleEntity defaultRoleForUserId(Long userId) {
        NeighborRoleEntity role = new NeighborRoleEntity();
        role.setUserId(userId);
        role.setUserRole(RoleEnum.ROLE_NB_OPERATOR);
        return role;
    }
}

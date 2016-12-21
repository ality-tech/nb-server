package org.neighbor.service;

import org.neighbor.entity.NeighborRole;
import org.neighbor.entity.RoleEnum;
import org.neighbor.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void createDefaultRoleForUserId(Long userId) {
        NeighborRole role = roleRepository.save(defaultRoleForUserId(userId));
        System.out.println(role);
    }

    private NeighborRole defaultRoleForUserId(Long userId) {

        NeighborRole role = new NeighborRole();
        role.setUserId(userId);
        role.setUserRole(RoleEnum.NB_OPERATOR);
        return role;
    }
}

package org.neighbor.service;

import org.neighbor.api.UserUrn;
import org.neighbor.api.dtos.UserDto;
import org.neighbor.entity.NeighborUser;
import org.neighbor.mappers.NeighborUserToUserDto;
import org.neighbor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private NeighborUserToUserDto neighborUserToUserDto;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

//    @Override
//    public NeighborUser createDefaultUserForAccountId(Long accountId) {
//        NeighborUser user = userRepository.save(defaultForAccount(accountId));
//        roleService.createDefaultRoleForUserId(user.getId());
//        return user;
//    }

    @Override
    public UserDto getUserByUrn(String urn) {
        UserUrn userUrn = new UserUrn(urn);
        NeighborUser neighborUser = userRepository.findByLogin(userUrn.getLogin());
        UserDto userDto = neighborUserToUserDto.userToUserDto(neighborUser);
        return userDto;
    }

    @Override
    public List<UserDto> getUsersByFilter(Object filter) {
        return new ArrayList<>();
    }

    @Override
    public boolean updateUser(NeighborUser user) {
        return false;
    }

//    @Override
//    public NeighborUser defaultForAccount(Long accountId) {
//        NeighborUser user = new NeighborUser();
//        user.setUserPhone("0");
//        user.setPinCode("0");//todo ???
//        user.setCreatedOn(new Date());
//        user.setAccountId(accountId);
//        user.setActivationStatus(ActivationStatus.ACTIVE);
//        user.setUserUrn("0");
//        user.setLogin("0");//todo ???
//        return user;
//    }
}

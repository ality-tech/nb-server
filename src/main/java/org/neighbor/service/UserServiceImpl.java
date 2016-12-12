package org.neighbor.service;

import org.neighbor.api.UserUrn;
import org.neighbor.api.dtos.UserDto;
import org.neighbor.entity.NeighborUser;
import org.neighbor.mappers.NeighborUserToUserDto;
import org.neighbor.repository.NeighborUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

   /* @Autowired
    private NeighborUserToUserDto neighborUserToUserDto;*/

    @Autowired
    private NeighborUserRepository userDao;

    @Override
    public UserDto getUserByUrn(String urn) {
        UserUrn userUrn = new UserUrn(urn);
        NeighborUser neighborUser = userDao.findByLogin(userUrn.getLogin());
//        UserDto userDto = neighborUserToUserDto.userToUserDto(neighborUser);
        return new UserDto();
    }

    @Override
    public List<UserDto> getUsersByFilter(Object filter) {
        return new ArrayList<>();
    }

    @Override
    public boolean updateUser(NeighborUser user) {
        return false;
    }
}

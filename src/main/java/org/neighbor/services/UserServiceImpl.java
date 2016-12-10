package org.neighbor.services;

import org.neighbor.entity.NeighborUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

//    @Autowired
//    private UserDao userDao;

    @Override
    public NeighborUser getUserByUrn(String urn) {
        return new NeighborUser();
    }

    @Override
    public List<NeighborUser> getUsersByFilter(Object filter) {
        return new ArrayList<>();
    }

    @Override
    public boolean updateUser(NeighborUser user) {
        return false;
    }
}

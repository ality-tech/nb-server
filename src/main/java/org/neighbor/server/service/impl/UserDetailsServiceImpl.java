package org.neighbor.server.service.impl;

import org.neighbor.api.user.ActivationStatus;
import org.neighbor.server.entity.RoleEntity;
import org.neighbor.server.entity.UserEntity;
import org.neighbor.server.repository.RoleRepository;
import org.neighbor.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<UserEntity> byLogin = userRepository.findByLogin(s);
        if (byLogin.isPresent()) {
            UserEntity user = byLogin.get();
            if (user.getActivationStatus() == ActivationStatus.ACTIVE) {

            }
            List<RoleEntity> roles = roleRepository.findByUserId(user.getId());
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            for (RoleEntity role : roles) {
                String role1 = role.getUserRole().name();
                grantedAuthorities.add(new SimpleGrantedAuthority(role1));
            }
            return new User(user.getLogin(), user.getPinCode(), grantedAuthorities);
        } else throw new UsernameNotFoundException(s + " not found");
    }
}

package com.company.authentication.service;

import com.company.authentication.entity.AppUser;
import com.company.authentication.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
;import java.util.HashSet;
import java.util.Set;
@Service
public class UserServiceImpl implements IAppUserService, UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepository;


    @Override
    public AppUser findByUserName(String username) {
        return appUserRepository.findByUserName(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByUserName(username);
        return buildUserFromUserEntity(user);
    }

    private User buildUserFromUserEntity(AppUser userEntity) {
        // convert model user to spring security user
        String username               = userEntity.getUserName();
        String password               = userEntity.getPassword();
        boolean enabled               = true;
        boolean accountNonExpired     = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked      = true;
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return userEntity.getRole();
            }
        });

        User springUser = new User(username,password,enabled,accountNonExpired,credentialsNonExpired,accountNonLocked,authorities);
        return springUser;
    }
}

package com.company.authentication;

import com.company.authentication.common.Encoders;
import com.company.authentication.entity.AppUser;
import com.company.authentication.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Component
public class AppAuthenticationProvider implements AuthenticationManager {

    @Autowired
    private UserServiceImpl userService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString(); // (1)
        String password = authentication.getCredentials().toString(); // (1)

        AppUser user = userService.findByUserName(username); // (2)
        // (3)
        if (user == null || !user.getPassword().equalsIgnoreCase(password)) {
            throw new AuthenticationServiceException("could not login");
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword(), authorities);
        return usernamePasswordAuthenticationToken; // (4)
    }

/*    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }*/
}

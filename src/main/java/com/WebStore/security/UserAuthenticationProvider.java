package com.WebStore.security;


import com.WebStore.entities.User;
import com.WebStore.repository.UserRepo;
import lombok.AllArgsConstructor;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class UserAuthenticationProvider implements AuthenticationProvider {
    private UserRepo repository;
    private PasswordEncoder encoder;


    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = repository.findByEmail(email);
        if (user == null) {
            throw new BadCredentialsException("User not found");
        }

        if (encoder.matches(password, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(user, password, getUserRoles(user.getRole()));
        } else {
            throw new BadCredentialsException("Password mismatch");
        }
    }


    private List<GrantedAuthority> getUserRoles(String userRoles) {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        String[] roles = userRoles.split(",");
        for (String role : roles) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(role.replaceAll("\\s+", "")));
        }
        return grantedAuthorityList;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

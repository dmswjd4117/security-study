package com.example.demo.security.auth.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class FormAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public FormAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String id = authentication.getName();
        String password = (String)authentication.getCredentials();

        UserDetails userDetails = userDetailsService.loadUserByUsername(id);

        if(userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("invalid user");
        }

        User user = (User) userDetails;
        return new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());

//        try{
//            UserDetails userDetails = userDetailsService.loadUserByUsername(id);
//
//            if(userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())){
//                throw new BadCredentialsException("invalid user");
//            }
//
//            User user = (User) userDetails;
//            return new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());
//        }catch (Exception exception){
//            throw new RuntimeException(exception.getMessage());
//        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

}

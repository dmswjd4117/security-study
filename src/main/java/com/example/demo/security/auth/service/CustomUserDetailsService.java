package com.example.demo.security.auth.service;

import com.example.demo.repository.MemberRepository;
import com.example.demo.security.auth.CustomUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByName(username)
                .map(findMember->{
                    Collection<? extends GrantedAuthority> auths = findMember.getGrantedAuthorities();
                    return new CustomUser(findMember, authoritiesMapper.mapAuthorities(auths));
                })
                .orElseThrow(()->new UsernameNotFoundException(username+": username not found"));
    }
}

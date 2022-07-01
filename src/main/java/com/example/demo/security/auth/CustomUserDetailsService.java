package com.example.demo.security.auth;

import com.example.demo.domain.user.Member;
import com.example.demo.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        Member member = memberRepository.findByUserName(username);
        if(member == null){
            throw new UsernameNotFoundException(username);
        }
        List<GrantedAuthority> roles = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        return new CustomUser(member, roles);
    }
}

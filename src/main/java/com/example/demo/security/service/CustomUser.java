package com.example.demo.security.service;

 import com.example.demo.domain.Member;
 import org.springframework.security.core.GrantedAuthority;
 import org.springframework.security.core.userdetails.User;
 import java.util.Collection;

public class CustomUser extends User {

    private final Member member;

    public CustomUser(Member member, Collection<? extends GrantedAuthority> auths){
        super(member.getName(), member.getPassword(), auths);
        this.member = member;
    }

    public Member getMember() {
        return member;
    }
}

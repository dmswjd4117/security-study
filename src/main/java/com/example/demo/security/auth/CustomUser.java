package com.example.demo.security.auth;

 import com.example.demo.domain.user.Member;
 import org.springframework.security.core.GrantedAuthority;
 import org.springframework.security.core.userdetails.User;
 import java.util.Collection;

public class CustomUser extends User {

    private final Member member;

    public CustomUser(Member member, Collection<? extends GrantedAuthority> auths){
        super(member.getUserName(), member.getPassword(), auths);
        this.member = member;
    }

    public Member getMember() {
        return member;
    }
}

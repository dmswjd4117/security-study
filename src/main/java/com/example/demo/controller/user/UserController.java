package com.example.demo.controller.user;


import com.example.demo.domain.Member;
import com.example.demo.service.MemberService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final MemberService memberService;

    public UserController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value="/member")
    public String createUser() {
        return "user/login/register";
    }

    @PostMapping(value="/member")
    public String createUser(@ModelAttribute RegisterRequest request) {
        ModelMapper modelMapper = new ModelMapper();
        Member member = modelMapper.map(request, Member.class);
        memberService.createUser(member);
        return "redirect:/";
    }
}

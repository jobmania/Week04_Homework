package com.sparta.post.controller;


import com.sparta.post.dto.MemberRequestDto;
import com.sparta.post.dto.ResponseDto;
import com.sparta.post.dto.TokenDto;
import com.sparta.post.security.UserDetailsImpl;
import com.sparta.post.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/api/member")
public class MemberController {

    @Autowired
    private final MemberService memberService;


    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    // 회원 가입
    @PostMapping("/signup")
    public ResponseDto<?> signup(@RequestBody MemberRequestDto memberRequestDto ) {
        return memberService.createMember(memberRequestDto) ;
    }


    // 회원 로그인(토큰 버젼)
    @PostMapping("/login")
    public ResponseDto<?> login(@RequestBody MemberRequestDto memberRequestDto) {


        HttpHeaders headers = new HttpHeaders();


        return memberService.loginMember(memberRequestDto);
    }



}
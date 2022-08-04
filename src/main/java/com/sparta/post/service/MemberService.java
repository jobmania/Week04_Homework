package com.sparta.post.service;

import com.sparta.post.domain.Member;
import com.sparta.post.domain.RefreshToken;
import com.sparta.post.dto.MemberRequestDto;
import com.sparta.post.dto.MemberResponseDto;
import com.sparta.post.dto.ResponseDto;
import com.sparta.post.dto.TokenDto;
import com.sparta.post.repository.MemberRepository;
import com.sparta.post.repository.RefreshTokenRepository;
import com.sparta.post.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

//    @Autowired
//    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
//        this.memberRepository = memberRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.tokenProvider = tokenProvider;
//    }


    @Transactional
    public ResponseDto<?> createMember(MemberRequestDto memberRequestDto) { // Member 생성, 회원가입
        if (memberRepository.existsByNickname(memberRequestDto.getNickname())) {  // 중복제거
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }

        Pattern patternName = Pattern.compile("^([a-zA-Z0-9]{4,12})$");  //\d{4,12}
        Pattern patternPw= Pattern.compile("^([a-z0-9]{4,32})$");    //\d{4,32}
        Boolean nameResult = patternName.matcher(memberRequestDto.getNickname()).matches() ;
        Boolean password = patternPw.matcher(memberRequestDto.getPassword()).matches();


        // 패스워드 암호화
        String codePassword = passwordEncoder.encode(memberRequestDto.getPassword());
        String codePasswordConfirm = passwordEncoder.encode(memberRequestDto.getPasswordConfirm());
        String Nickname = memberRequestDto.getNickname();
        Member member = new Member(Nickname,codePassword,codePasswordConfirm);

        if(nameResult && password){ // 조건확인
            if(memberRequestDto.getPassword().equals(memberRequestDto.getPasswordConfirm())){  // 패스워드확인
                memberRepository.save(member);
                return ResponseDto.success(member);
            }else{ return ResponseDto.fail("NOT_FOUND", "패스워드를 똑같이 입력하세요!");  }

        }else { return ResponseDto.fail("NOT_FOUND", "아이디, 패스워도 조건을 확인하세요!");   }
    }


//
//    @Transactional(readOnly = true)
//    public ResponseDto<?> loginMember(MemberRequestDto memberRequestDto) {  // 로그인하기. 토큰 추가해아함....
//          Member member = memberRepository.findByNickname(memberRequestDto.getNickname())
//                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
//        return ResponseDto.success(member);
//    }




    @Transactional
    public ResponseDto<?> loginMember(MemberRequestDto memberRequestDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = memberRequestDto.toAuthentication();
        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);


        // 5. 토큰 발급
        List newList = new ArrayList<>();
        newList.add(memberRequestDto);
        newList.add(tokenDto);
        return ResponseDto.success(newList);
    }







}

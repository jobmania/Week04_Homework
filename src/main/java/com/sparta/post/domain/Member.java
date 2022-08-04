package com.sparta.post.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.post.dto.MemberRequestDto;
import com.sparta.post.dto.MemberResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "members")
public class Member extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (unique = true)
    private String nickname;

    @Column
    @JsonIgnore
    private String password;

    @Column
    @JsonIgnore
    private String passwordConfirm;


    public Member(String nickname, String password, String passwordConfirm) {
        this.nickname = nickname;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    public Member(MemberRequestDto memberRequestDto){
        this.nickname = memberRequestDto.getNickname();
        this.password = memberRequestDto.getPassword();
        this.passwordConfirm = memberRequestDto.getPasswordConfirm();
    }


    public Member(MemberResponseDto memberResponseDto){
        this.id = memberResponseDto.getId();
        this.nickname = memberResponseDto.getNickname();

    }


}

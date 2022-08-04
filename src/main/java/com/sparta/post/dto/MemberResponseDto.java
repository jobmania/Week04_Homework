package com.sparta.post.dto;

import com.sparta.post.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter

public class MemberResponseDto {

    private Long id;
    private String nickname;


    public MemberResponseDto(Optional member) {

    }
}

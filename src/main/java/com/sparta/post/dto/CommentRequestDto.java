package com.sparta.post.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {


    private String postid;
    private String content;
    private String author;

}

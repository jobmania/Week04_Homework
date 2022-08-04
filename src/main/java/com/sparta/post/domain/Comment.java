package com.sparta.post.domain;

import com.sparta.post.dto.CommentRequestDto;
import com.sparta.post.dto.MemberRequestDto;
import com.sparta.post.dto.PostRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String postid;  // 포스트에서 들고와야되고


    @Column(columnDefinition = "TEXT")
    private String content;

    @Column
    private String author;




    public Comment(CommentRequestDto commentRequestDto, MemberRequestDto memberRequestDto){
        this.postid = commentRequestDto.getPostid();
        this.content = commentRequestDto.getContent();
        this.author = memberRequestDto.getNickname();

    }

    public void update(CommentRequestDto commentRequestDto) {
        this.postid = commentRequestDto.getPostid();
        this.content = commentRequestDto.getContent();
    }




}

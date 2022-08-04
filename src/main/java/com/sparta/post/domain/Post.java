package com.sparta.post.domain;


import com.sparta.post.dto.MemberRequestDto;
import com.sparta.post.dto.PostChangeDto;
import com.sparta.post.dto.PostRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Optional;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String title;

  @Column( columnDefinition = "TEXT")
  private String content;

  @Column
  private String author;

//  @JsonIgnore
//  @Column(nullable = false)
//  private String password;

  public Post(PostRequestDto postRequestDto, String nickname) {
    this.title = postRequestDto.getTitle();
    this.content = postRequestDto.getContent();
    this.author = nickname;
//    this.password = postRequestDto.getPassword();
  }


  public Post(PostChangeDto postChangeDto){
    this.title = postChangeDto.getTitle();
    this.content = postChangeDto.getContent();
    this.author = postChangeDto.getAuthor();
  }


  public void update(PostRequestDto postRequestDto) {
    this.title = postRequestDto.getTitle();
    this.content = postRequestDto.getContent();
//    this.author = postChangeDto.getAuthor();
//    this.password = postRequestDto.getPassword();
  }

}

package com.sparta.post.controller;


import com.sparta.post.dto.MemberRequestDto;
import com.sparta.post.dto.PostChangeDto;
import com.sparta.post.dto.PostRequestDto;
import com.sparta.post.dto.ResponseDto;
import com.sparta.post.security.UserDetailsImpl;
import com.sparta.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostController {


  private final PostService postService;

  @PostMapping("api/auth/post")  // 게시물 생성                                      //인터페이스를 선언...
  public ResponseDto<?> createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetails userDetails) {
    System.out.println(requestDto);
    System.out.println(userDetails.getUsername());
    return postService.createPost(requestDto, userDetails.getUsername()); // 토큰을 들고오기
  }

  @GetMapping("/api/post/{id}")    //하나조회
  public ResponseDto<?> getPost(@PathVariable Long id) {
    return postService.getPost(id);
  }

  @GetMapping("/api/post")   //전체조회
  public ResponseDto<?> getAllPosts() {
    return postService.getAllPost();
  }

  @PutMapping("/api/auth/post/{id}") // 수정
  public ResponseDto<?> updatePost(
          @PathVariable Long id, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetails userDetails)
  {
    return postService.updatePost(id, postRequestDto,userDetails.getUsername());
  }

  @DeleteMapping("/api/auth/post/{id}") // 삭제
  public ResponseDto<?> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {

    return postService.deletePost(id,userDetails.getUsername());
  }


}

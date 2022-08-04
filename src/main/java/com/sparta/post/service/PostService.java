package com.sparta.post.service;

import com.sparta.post.domain.Post;
import com.sparta.post.dto.MemberRequestDto;
import com.sparta.post.dto.PostChangeDto;
import com.sparta.post.dto.PostRequestDto;
import com.sparta.post.dto.ResponseDto;
import com.sparta.post.repository.MemberRepository;
import com.sparta.post.repository.PostRepository;
import com.sparta.post.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {


    private final PostRepository postRepository;



    @Transactional  // 게시글 작성, 멤버 닉네임을 author로 넣었다.
                 // 문제점 !!! api요청할때 memberdto 값이 null임..
    public ResponseDto<?> createPost(PostRequestDto requestDto, String nickname) {

        Post post = new Post(requestDto,nickname);
        postRepository.save(post);
        return ResponseDto.success(post);
    }



    @Transactional(readOnly = true)  // 게시글 전체 조회
    public ResponseDto<?> getAllPost() {
        return ResponseDto.success(postRepository.findAllByOrderByModifiedAtDesc());
    }



    @Transactional(readOnly = true)  // 게시글 상세 조회
    public ResponseDto<?> getPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            return ResponseDto.fail("NULL_POST_ID", "post id isn't exist");
        }

        return ResponseDto.success(optionalPost.get());
    }



    @Transactional       // 게시글 수정
    public ResponseDto<Post> updatePost(Long id, PostRequestDto postRequestDto, String author) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            return ResponseDto.fail("NULL_POST_ID", "post id isn't exist");
        }

        Post post =optionalPost.get();

        if (!post.getAuthor().equals(author)) {
            throw new IllegalArgumentException("게시글 작성자만 수정할 수 있습니다.");
        }

        post.update(postRequestDto);
        return ResponseDto.success(post);
    }



    @Transactional      // 게시글 삭제
    public ResponseDto<?> deletePost(Long id, String author) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            return ResponseDto.fail("NOT_FOUND", "post id is not exist");
        }

        Post post =optionalPost.get();

        if (!post.getAuthor().equals(author)) {
            throw new IllegalArgumentException("게시글 작성자만 삭제할 수 있습니다.");
        }else {
            postRepository.delete(post);
            return ResponseDto.success(true);
        }



    }





}

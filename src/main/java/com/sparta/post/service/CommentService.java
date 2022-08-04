package com.sparta.post.service;

import com.sparta.post.domain.Comment;
import com.sparta.post.domain.Post;
import com.sparta.post.dto.CommentRequestDto;
import com.sparta.post.dto.MemberRequestDto;
import com.sparta.post.dto.ResponseDto;
import com.sparta.post.repository.CommentRepository;
import com.sparta.post.repository.MemberRepository;
import com.sparta.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;


    public CommentService(PostRepository postRepository, CommentRepository commentRepository, MemberRepository memberRepository){
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional/////// 생성
    public ResponseDto<?> createComment(CommentRequestDto commentRequestDto, MemberRequestDto memberRequestDto) {
        Comment comment = new Comment(commentRequestDto,memberRequestDto);
        commentRepository.save(comment);
        return ResponseDto.success(comment);
    }

    @Transactional //전체 조회
    public ResponseDto<?> getAllComment() {
        return ResponseDto.success(commentRepository.findAllByOrderByModifiedAtDesc());
    }

    @Transactional   // 업데이트
    public ResponseDto<?> updateComment(Long id, CommentRequestDto commentRequestDto) {

        Optional<Comment> optionalComment = commentRepository.findById(id);

        if (optionalComment.isEmpty()) {
            return ResponseDto.fail("NULL_POST_ID", "post id isn't exist");
        }

        Comment comment = optionalComment.get();
        comment.update(commentRequestDto);
        return ResponseDto.success(comment);

    }

    public ResponseDto<?> deleteComment(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);


        if (optionalComment.isEmpty()) {
            return ResponseDto.fail("NOT_FOUND", "post id is not exist");
        }

        Comment comment = optionalComment.get();
        commentRepository.delete(comment);
        return ResponseDto.success(true);

    }
}

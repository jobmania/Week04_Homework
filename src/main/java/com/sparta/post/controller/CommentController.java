package com.sparta.post.controller;


import com.sparta.post.dto.CommentRequestDto;
import com.sparta.post.dto.MemberRequestDto;
import com.sparta.post.dto.ResponseDto;
import com.sparta.post.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;

    }

    @PostMapping("/api/auth/comment")  // 댓글 생성.
    public ResponseDto<?> createComment(@RequestBody CommentRequestDto commentRequestDto, MemberRequestDto memberRequestDto){
        return commentService.createComment(commentRequestDto,memberRequestDto);

    }

    @GetMapping("/api/comment/{id}") // 댓글 하나 조회
    public ResponseDto<?> getComment(){
        return commentService.getAllComment();
    }

    @PutMapping("/api/auth/comment/{id}")
    public ResponseDto<?> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto){
        return commentService.updateComment(id,commentRequestDto);
    }


    @DeleteMapping("/api/auth/comment/{id}")
    public ResponseDto<?> deleteComment(@PathVariable Long id){
        return commentService.deleteComment(id);
    }





}

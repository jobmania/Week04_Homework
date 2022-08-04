package com.sparta.post.repository;

import com.sparta.post.domain.Comment;
import com.sparta.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findById(Long id);
    List<Comment> findAllByOrderByModifiedAtDesc();
}

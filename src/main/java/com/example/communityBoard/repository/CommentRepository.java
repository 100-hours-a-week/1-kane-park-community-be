package com.example.communityBoard.repository;

import com.example.communityBoard.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 게시글 댓글 목록 조회
    List<Comment> findByBoardId(Long boardId);
    long countByBoardId(Long boardId);
}
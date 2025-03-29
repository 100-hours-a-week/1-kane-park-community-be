package com.example.communityBoard.service;

import com.example.communityBoard.dto.CommentDto;
import com.example.communityBoard.dto.CommentRequestDto;
import com.example.communityBoard.entity.Board;
import com.example.communityBoard.entity.Comment;
import com.example.communityBoard.entity.User;
import com.example.communityBoard.repository.BoardRepository;
import com.example.communityBoard.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    // 댓글 작성
    public void addComment(Long boardId, CommentRequestDto dto, User writer) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .writer(writer)
                .board(board)
                .createdAt(java.time.LocalDateTime.now())
                .build();

        commentRepository.save(comment);
    }

    // 특정 게시글 댓글 목록 조회
    public List<CommentDto> getCommentsByBoard(Long boardId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return commentRepository.findByBoardId(boardId).stream()
                .map(comment -> new CommentDto(
                        comment.getId(),
                        comment.getContent(),
                        comment.getWriter().getEmail(),
                        comment.getCreatedAt().format(formatter)
                ))
                .collect(Collectors.toList());
    }

    // 댓글 수정
    public void updateComment(Long commentId, CommentRequestDto dto, User currentUser) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다."));

        if (!comment.getWriter().getId().equals(currentUser.getId())) {
            throw new RuntimeException("작성자만 수정할 수 있습니다.");
        }

        comment.setContent(dto.getContent());
        commentRepository.save(comment);
    }

    // 댓글 삭제 (작성자만 가능)
    public void deleteComment(Long commentId, User currentUser) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다."));

        if (!comment.getWriter().getId().equals(currentUser.getId())) {
            throw new RuntimeException("작성자만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);
    }

    // 댓글 카운트
    public long countCommentsByBoard(Long boardId) {
        return commentRepository.countByBoardId(boardId);
    }
}
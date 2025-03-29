package com.example.communityBoard.controller;

import com.example.communityBoard.dto.CommentDto;
import com.example.communityBoard.dto.CommentRequestDto;
import com.example.communityBoard.entity.User;
import com.example.communityBoard.service.AuthService;
import com.example.communityBoard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final AuthService authService;

    // 댓글 작성
    @PostMapping("/board/{id}/comment")
    public ResponseEntity<String> addComment(@PathVariable Long id,
                                             @RequestBody CommentRequestDto dto) {
        User writer = authService.getLoginUser();
        commentService.addComment(id, dto, writer);
        return ResponseEntity.ok("댓글 작성 완료");
    }

    // 댓글 목록 조회
    @GetMapping("/board/{id}/comments")
    public List<CommentDto> getComments(@PathVariable Long id) {
        return commentService.getCommentsByBoard(id);
    }

    // 댓글 수정
    @PutMapping("/comment/{id}")
    public ResponseEntity<String> updateComment(@PathVariable Long id,
                                                @RequestBody CommentRequestDto dto) {
        User currentUser = authService.getLoginUser();
        commentService.updateComment(id, dto, currentUser);
        return ResponseEntity.ok("댓글 수정 완료");
    }

    // 댓글 삭제 (작성자만)
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        User currentUser = authService.getLoginUser();
        commentService.deleteComment(id, currentUser);
        return ResponseEntity.ok("댓글 삭제 완료");
    }

    // 댓글 카운트
    @GetMapping("/board/{id}/comments/count")
    public ResponseEntity<Long> countComments(@PathVariable Long id) {
        long count = commentService.countCommentsByBoard(id);
        return ResponseEntity.ok(count);
    }
}
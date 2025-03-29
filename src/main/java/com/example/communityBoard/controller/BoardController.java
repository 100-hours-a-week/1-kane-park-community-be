package com.example.communityBoard.controller;

import com.example.communityBoard.dto.BoardDto;
import com.example.communityBoard.dto.BoardRequestDto;
import com.example.communityBoard.dto.BoardSummaryDto;
import com.example.communityBoard.entity.User;
import com.example.communityBoard.repository.UserRepository;
import com.example.communityBoard.service.AuthService;
import com.example.communityBoard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final AuthService authService;
    private final UserRepository userRepository;

    private User getLoginUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("로그인 정보를 찾을 수 없습니다."));
    }

    // 게시글 목록 조회
    @GetMapping("/board")
    public List<BoardSummaryDto> getBoardList() {
        return boardService.getAllBoards();
    }

    // 게시글 상세 조회
    @GetMapping("/board/{id}")
    public ResponseEntity<BoardDto> getBoard(@PathVariable Long id) {
        BoardDto board = boardService.getBoardById(id);
        return ResponseEntity.ok(board);
    }

// authService 분리 전
//    // 게시글 작성
//    @PostMapping("/board")
//    public ResponseEntity<String> createBoard(@RequestBody BoardRequestDto dto) {
//        // 로그인한 사용자 정보 가져오기
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();
//
//        // 사용자 엔티티 조회
//        User writer = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("사용자 정보를 찾을 수 없습니다."));
//
//        boardService.createBoard(dto, writer);
//        return ResponseEntity.ok("게시글 작성 완료");
//    }
//
//    // 게시물 수정
//    @PutMapping("/board/{id}")
//    public ResponseEntity<String> updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto dto) {
//        User currentUser = getLoginUser();
//        boardService.updateBoard(id, dto, currentUser);
//        return ResponseEntity.ok("게시글 수정 완료");
//    }
//
//    // 게시물 삭제
//    @DeleteMapping("/board/{id}")
//    public ResponseEntity<String> deleteBoard(@PathVariable Long id) {
//        User currentUser = getLoginUser();
//        boardService.deleteBoard(id, currentUser);
//        return ResponseEntity.ok("게시글 삭제 완료");
//    }

    // 게시글 작성
    @PostMapping("/board")
    public ResponseEntity<String> createBoard(@RequestBody BoardRequestDto dto) {
        User writer = authService.getLoginUser();
        boardService.createBoard(dto, writer);
        return ResponseEntity.ok("게시글 작성 완료");
    }

    // 게시글 수정 (작성자만 가능)
    @PutMapping("/board/{id}")
    public ResponseEntity<String> updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto dto) {
        User currentUser = authService.getLoginUser();
        boardService.updateBoard(id, dto, currentUser);
        return ResponseEntity.ok("게시글 수정 완료");
    }

    // 게시글 삭제 (작성자만 가능)
    @DeleteMapping("/board/{id}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long id) {
        User currentUser = authService.getLoginUser();
        boardService.deleteBoard(id, currentUser);
        return ResponseEntity.ok("게시글 삭제 완료");
    }

    // 조회수 증가
    @PutMapping("/board/{id}/view")
    public ResponseEntity<String> increaseView(@PathVariable Long id) {
        boardService.increaseViewCount(id);
        return ResponseEntity.ok("조회수 증가");
    }

    // 좋아요 증가
    @PutMapping("/board/{id}/like")
    public ResponseEntity<String> likeBoard(@PathVariable Long id) {
        boardService.increaseLike(id);
        return ResponseEntity.ok("좋아요 증가");
    }
}
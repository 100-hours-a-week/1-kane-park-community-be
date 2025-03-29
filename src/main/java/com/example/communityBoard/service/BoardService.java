package com.example.communityBoard.service;

import com.example.communityBoard.dto.BoardDto;
import com.example.communityBoard.dto.BoardRequestDto;
import com.example.communityBoard.dto.BoardSummaryDto;
import com.example.communityBoard.entity.Board;
import com.example.communityBoard.entity.User;
import com.example.communityBoard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // 게시글 목록 조회
    public List<BoardSummaryDto> getAllBoards() {
        return boardRepository.findAll().stream()
                .map(board -> new BoardSummaryDto(
                        board.getId(),
                        board.getTitle()
                ))
                .collect(Collectors.toList());
    }

    // 게시글 작성
    public void createBoard(BoardRequestDto dto, User writer) {
        Board board = Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .createdAt(LocalDateTime.now())
                .writer(writer)
                .build();

        boardRepository.save(board);
    }

    // 게시글 상세보기
    public BoardDto getBoardById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        return new BoardDto(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getWriter().getEmail(),
                board.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                board.getViewCount(),
                board.getLikeCount()
        );
    }

    // 게시물 수정
    public void updateBoard(Long id, BoardRequestDto dto, User currentUser) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        // 작성자 권한 검사
        if (!board.getWriter().getId().equals(currentUser.getId())) {
            throw new RuntimeException("작성자만 수정할 수 있습니다.");
        }

        board.setTitle(dto.getTitle());
        board.setContent(dto.getContent());
        boardRepository.save(board);
    }

    // 게시물 삭제
    public void deleteBoard(Long id, User currentUser) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        // 작성자 권한 검사
        if (!board.getWriter().getId().equals(currentUser.getId())) {
            throw new RuntimeException("작성자만 삭제할 수 있습니다.");
        }

        boardRepository.delete(board);
    }

    // 조회수 증가
    public void increaseViewCount(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));
        board.setViewCount(board.getViewCount() + 1);
        boardRepository.save(board);
    }

    // 좋아요 증가
    public void increaseLike(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        board.setLikeCount(board.getLikeCount() + 1);
        boardRepository.save(board);
    }
}
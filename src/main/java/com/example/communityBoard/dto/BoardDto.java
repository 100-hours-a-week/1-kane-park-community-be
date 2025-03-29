package com.example.communityBoard.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private Long id;
    private String title;
    private String content;
    private String writerEmail;
    private String createdAt;
    private int viewCount;
    private int likeCount;
}
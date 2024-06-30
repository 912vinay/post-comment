package com.postcomment.dto;

import lombok.Data;

@Data
public class CommentResponseDTO {
    private Long id;
    private String content;
    private Long postId;

    public CommentResponseDTO(Long id, String content, Long postId) {
        this.id = id;
        this.content = content;
        this.postId = postId;
    }
}

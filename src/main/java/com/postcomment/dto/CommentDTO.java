package com.postcomment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentDTO {
    @NotBlank(message = "Content is mandatory")
    private String content;
}

package com.postcomment.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class PostDTO {

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Content is mandatory")
    private String content;


}

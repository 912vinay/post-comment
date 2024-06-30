package com.postcomment.controller;


import com.postcomment.dto.CommentDTO;
import com.postcomment.dto.CommentResponseDTO;
import com.postcomment.entities.Comment;
import com.postcomment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/comments")
@RequiredArgsConstructor
@Validated
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/post/{postId}")
    public ResponseEntity<Comment> createComment(@PathVariable Long postId,@RequestBody @Valid CommentDTO commentDTO) {
        Optional<Comment> commentOptional = commentService.saveCommentWithRef(commentDTO, postId);
        return commentOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CommentResponseDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(commentService.getAllCommentsWithPosts(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}

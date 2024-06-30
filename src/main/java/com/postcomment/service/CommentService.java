package com.postcomment.service;


import com.postcomment.dto.CommentDTO;
import com.postcomment.dto.CommentResponseDTO;
import com.postcomment.entities.Comment;
import com.postcomment.entities.Post;
import com.postcomment.repository.CommentRepository;
import com.postcomment.repository.PostRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;


    @Transactional
    public Optional<Comment> saveComment(@Valid CommentDTO commentDTO, Long postId) {
        log.debug("Saving comment: {}", commentDTO);

       Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            Comment comment = new Comment();
            comment.setContent(commentDTO.getContent());
            post.addComment(comment);
            commentRepository.save(comment);  // Save the comment to persist it
            return Optional.of(comment);
       }

        return Optional.empty();
    }


    public Optional<Comment> saveCommentWithRef(@Valid CommentDTO commentDTO, Long postId) {
        log.debug("Saving comment: {}", commentDTO);

        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setPost(postRepository.getReferenceById(postId));
        commentRepository.save(comment);
        return Optional.of(comment);
    }

    @Transactional
    public Optional<Comment> saveCommentWithRef3(@Valid CommentDTO commentDTO, Long postId) {
        log.debug("Saving comment: {}", commentDTO);

        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Comment comment = new Comment();
            comment.setContent(commentDTO.getContent());
            comment.setPost(postOptional.get());
            commentRepository.save(comment);
            return Optional.of(comment);
        } else {
            return Optional.empty();
        }
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        log.debug("Fetching comments for post id: {}", postId);
        return commentRepository.findByPostId(postId);
    }

    public List<CommentResponseDTO> getAllCommentsWithPosts(Pageable pageable) {
        Page<Comment> comments = commentRepository.findAllWithPosts(pageable);
        return comments.stream()
                .map(comment -> new CommentResponseDTO(comment.getId(), comment.getContent(), comment.getPost().getId()))
                .collect(Collectors.toList());
    }
    public Page<Comment> getAll(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    public void deleteComment(Long id) {
        log.debug("Deleting comment by id: {}", id);
        commentRepository.deleteById(id);
    }
}

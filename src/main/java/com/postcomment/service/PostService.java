package com.postcomment.service;


import com.postcomment.dto.PostDTO;
import com.postcomment.entities.Post;
import com.postcomment.repository.PostRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;

    public Post savePost(@Valid PostDTO post) {
        log.debug("Saving post: {}", post);
        Post build = Post.builder().title(post.getTitle()).content(post.getContent()).build();
        return postRepository.save(build);
    }

    public Optional<Post> getPostById(Long id) {
        log.debug("Fetching post by id: {}", id);
        return postRepository.findByIdWithAllComments(id);
    }

    public List<Post> findAll(Pageable pageable) {
        return postRepository.findAllWithComments(pageable);
    }

    public void deletePost(Long id) {
        log.debug("Deleting post by id: {}", id);
        postRepository.deleteById(id);
    }
}

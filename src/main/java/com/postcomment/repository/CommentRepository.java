package com.postcomment.repository;

import com.postcomment.entities.Comment;
import com.postcomment.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);

    @EntityGraph(attributePaths = {"post"})
    @Query("SELECT c FROM Comment c")
    Page<Comment> findAllWithPosts(Pageable pageable);
}

package lt.codeacademy.project.blog.service;

import lt.codeacademy.project.blog.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CommentService {
    void addComment(Comment comment);

    Comment getCommentById(UUID id);

    void updateComment(Comment comment);

    void deleteComment(UUID id);

    Page<Comment> getCommentsByBlogPostId(UUID id, Pageable pageable);

    boolean validateIsUserComment(UUID id, UUID userId, String role);
}

package lt.codeacademy.project.blog.service;

import lombok.extern.slf4j.Slf4j;
import lt.codeacademy.project.blog.exception.CommentFoundException;
import lt.codeacademy.project.blog.model.Comment;
import lt.codeacademy.project.blog.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void addComment(Comment comment) {
        try {
            commentRepository.save(comment);
        } catch (IllegalArgumentException e) {
            log.error("Cannot create Comment {}", comment);
        }
    }

    @Override
    public Comment getCommentById(UUID id) {
        return commentRepository.findById(id).orElseThrow(CommentFoundException::new);
    }

    @Override
    public void updateComment(Comment comment) {
        try {
            commentRepository.save(comment);
        } catch (IllegalArgumentException e) {
            log.error("Cannot create Comment {}", comment);
        }
    }

    @Override
    public void deleteComment(UUID id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Page<Comment> getCommentsByBlogPostId(UUID id, Pageable pageable) {
        return commentRepository.getCommentsByBlogPostId(id, pageable);
    }

    @Override
    public boolean validateIsUserComment(UUID id, UUID userId, String role) {
        Comment comment = commentRepository.findById(id).orElseThrow(CommentFoundException::new);

        if (userId == null || role == null) {
            return false;
        } else if (role.equals("ADMIN")) {
            return true;
        }
        return comment.getUser().getUserId().equals(userId);
    }
}

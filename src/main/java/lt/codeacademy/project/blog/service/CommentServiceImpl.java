package lt.codeacademy.project.blog.service;

import lt.codeacademy.project.blog.exception.CommentFoundException;
import lt.codeacademy.project.blog.model.Comment;
import lt.codeacademy.project.blog.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public Comment getCommentById(UUID id) {
        return commentRepository.findById(id).orElseThrow(CommentFoundException::new);
    }

    @Override
    public Page<Comment> getAllCommentsWithPages(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    public void updateComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(UUID id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Page<Comment> getCommentsByBlogPostId(UUID id, Pageable pageable) {
        return commentRepository.getCommentsByBlogPostId(id ,pageable);
    }
}

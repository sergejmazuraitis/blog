package lt.codeacademy.project.blog.repository;

import lt.codeacademy.project.blog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
}

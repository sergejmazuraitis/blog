package lt.codeacademy.project.blog.repository;

import lt.codeacademy.project.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findByName(String name);
}

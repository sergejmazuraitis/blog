package lt.codeacademy.project.blog.repository;

import lt.codeacademy.project.blog.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BlogPostRepository extends JpaRepository<BlogPost, UUID> {
    List<BlogPost> findByName(String name);
    List<BlogPost> findFirst5ByOrderByDateDesc();
    List<BlogPost> getDistinctByCategoryNotNull();
}

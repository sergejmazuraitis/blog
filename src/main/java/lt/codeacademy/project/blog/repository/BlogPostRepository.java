package lt.codeacademy.project.blog.repository;

import lt.codeacademy.project.blog.model.BlogPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BlogPostRepository extends JpaRepository<BlogPost, UUID> {
    List<BlogPost> findFirst5ByOrderByDateDesc();

    @Query("SELECT DISTINCT b.category FROM BlogPost b")
    List<String> getDistinctCategoriesNative();

    Page<BlogPost> findBlogPostsByCategory(String category, Pageable pageable);
}

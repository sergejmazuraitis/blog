package lt.codeacademy.project.blog.service;

import lt.codeacademy.project.blog.model.BlogPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface BlogPostService {
    void addBlogPost(BlogPost post);

    BlogPost getBlogPostById(UUID id);

    BlogPost getBlogPostByName(String name);

    Page<BlogPost> getAllBlogPostsWithPages(Pageable pageable);

    void updateBlogPost(BlogPost post);

    void deleteBlogPost(UUID id);

    List<BlogPost> findLastFivePost();

    List<BlogPost> findAllCategories();
}

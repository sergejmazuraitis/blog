package lt.codeacademy.project.blog.service;

import lombok.extern.slf4j.Slf4j;
import lt.codeacademy.project.blog.exception.BlogPostFoundException;
import lt.codeacademy.project.blog.model.BlogPost;
import lt.codeacademy.project.blog.repository.BlogPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class BlogPostServiceImpl implements BlogPostService {
    private final BlogPostRepository blogPostRepository;

    public BlogPostServiceImpl(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    @Override
    public void addBlogPost(BlogPost blogPost) {
        try {
            blogPostRepository.save(blogPost);
        } catch (IllegalArgumentException e) {
            log.error("Cannot create BlogPost {}", blogPost);
        }
    }

    @Override
    public BlogPost getBlogPostById(UUID id) {
        return blogPostRepository.findById(id).orElseThrow(BlogPostFoundException::new);
    }

    @Override
    public Page<BlogPost> getAllBlogPostsWithPages(Pageable pageable) {
        return blogPostRepository.findAll(pageable);
    }

    @Override
    public void updateBlogPost(BlogPost blogPost) {

        try {
            blogPostRepository.save(blogPost);
        } catch (IllegalArgumentException e) {
            log.error("Cannot create BlogPost {}", blogPost);
        }
    }

    @Override
    public void deleteBlogPost(UUID id) {
        blogPostRepository.deleteById(id);
    }

    @Override
    public List<BlogPost> findLastFivePost() {
        return blogPostRepository.findFirst5ByOrderByDateDesc();
    }

    @Override
    public List<String> findAllDistinctCategories() {
        List<String> blogPosts = blogPostRepository.getDistinctCategoriesNative();
        return blogPosts;
    }

    @Override
    public Page<BlogPost> findBlogPostsByCategory(String category, Pageable pageable) {
        return blogPostRepository.findBlogPostsByCategory(category, pageable);
    }
}

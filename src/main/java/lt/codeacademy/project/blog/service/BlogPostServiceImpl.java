package lt.codeacademy.project.blog.service;

import lt.codeacademy.project.blog.exception.BlogPostFoundException;
import lt.codeacademy.project.blog.model.BlogPost;
import lt.codeacademy.project.blog.repository.BlogPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BlogPostServiceImpl implements BlogPostService {
    private final BlogPostRepository blogPostRepository;

    public BlogPostServiceImpl(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    @Override
    public void addBlogPost(BlogPost blogPost) {
        blogPostRepository.save(blogPost);
    }

    @Override
    public BlogPost getBlogPostById(UUID id) {
        return blogPostRepository.findById(id).orElseThrow(BlogPostFoundException::new);
    }

    @Override
    public BlogPost getBlogPostByName(String name) {
        return blogPostRepository.findByName(name).get(0);
    }

    @Override
    public Page<BlogPost> getAllBlogPostsWithPages(Pageable pageable) {
        return blogPostRepository.findAll(pageable);
    }

    @Override
    public void updateBlogPost(BlogPost blogPost) {
        blogPostRepository.save(blogPost);
    }

    @Override
    public void deleteBlogPost(UUID id) {
        blogPostRepository.deleteById(id);
    }
}

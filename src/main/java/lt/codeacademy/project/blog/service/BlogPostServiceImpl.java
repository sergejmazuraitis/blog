package lt.codeacademy.project.blog.service;

import lt.codeacademy.project.blog.exception.BlogPostFoundException;
import lt.codeacademy.project.blog.model.BlogPost;
import lt.codeacademy.project.blog.repository.BlogPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class BlogPostServiceImpl implements BlogPostService {
    private final BlogPostRepository blogPostRepository;

    public BlogPostServiceImpl(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
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

    @Override
    public List<BlogPost> findLastFivePost() {
        return blogPostRepository.findFirst5ByOrderByDateDesc();
    }

    @Override
    public List<String> findAllDistinctCategories() {
        List<String> blogPosts = blogPostRepository.getDistinctCategoriesNative();
//        return blogPosts.stream().filter(distinctByKey(BlogPost::getCategory)).collect(Collectors.toList());
        return blogPosts;
    }

    @Override
    public Page<BlogPost> findBlogPostsByCategory(String category, Pageable pageable) {
        return blogPostRepository.findBlogPostsByCategory(category, pageable);
    }
}

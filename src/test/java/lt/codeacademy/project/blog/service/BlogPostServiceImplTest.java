package lt.codeacademy.project.blog.service;

import lt.codeacademy.project.blog.exception.BlogPostFoundException;
import lt.codeacademy.project.blog.model.BlogPost;
import lt.codeacademy.project.blog.repository.BlogPostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class BlogPostServiceImplTest {

    @Mock
    private BlogPostRepository blogPostRepository;

    @Mock
    private Pageable pageable;

    @Mock
    private BlogPost blogPost;

    @InjectMocks
    private BlogPostServiceImpl blogPostService;


    @Test
    public void testAddBlogPostWhenBlogPostIsNull() {
        doThrow(IllegalArgumentException.class).when(blogPostRepository).save(eq(null));

        blogPostService.addBlogPost(null);

        verify(blogPostRepository, times(1)).save(eq(null));
    }

    @Test
    public void testAddBlogPost(){
        when(blogPostRepository.save(blogPost)).thenReturn(blogPost);

        blogPostService.addBlogPost(blogPost);

        verify(blogPostRepository, times(1)).save(blogPost);
    }

    @Test
    public void testGetBlogPostByIdWhenBlogPostNotExist() {
        when(blogPostRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(null));

        assertThrows(BlogPostFoundException.class, () -> blogPostService.getBlogPostById(UUID.randomUUID()));
    }

    @Test
    public void testGetBlogPostByNameWhenBlogPostExist() {
        UUID id = UUID.randomUUID();
        when(blogPostRepository.findById(id)).thenReturn(Optional.of(blogPost));
        BlogPost post = blogPostService.getBlogPostById(id);

        assertEquals(blogPost, post);
    }

    @Test
    public void testGetAllBlogPostsWithPagesWhenBlogPostNotExists() {
    }

    @Test
    public void testGetAllBlogPostsWithPagesWhenBlogPostExists() {
    }


    @Test
    void testUpdateBlogPostWhenBlogPostNotExist() {
        doThrow(IllegalArgumentException.class).when(blogPostRepository).save(eq(null));

        blogPostService.updateBlogPost(null);

        verify(blogPostRepository, times(1)).save(eq(null));
    }


    @Test
    void testUpdateBlogPostWhenBlogPostExist() {
        when(blogPostRepository.save(blogPost)).thenReturn(blogPost);
        blogPostService.updateBlogPost(blogPost);

        verify(blogPostRepository, times(1)).save(blogPost);
    }

    @Test
    void deleteBlogPost() {
    }

    @Test
    void findLastFivePost() {
    }

    @Test
    void findAllDistinctCategories() {
    }

    @Test
    void findBlogPostsByCategory() {
    }
}
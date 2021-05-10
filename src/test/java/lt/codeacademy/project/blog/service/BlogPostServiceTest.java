package lt.codeacademy.project.blog.service;

import lt.codeacademy.project.blog.exception.BlogPostFoundException;
import lt.codeacademy.project.blog.model.BlogPost;
import lt.codeacademy.project.blog.repository.BlogPostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlogPostServiceTest {

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
    public void testAddBlogPostWhenBlogPostExist() {
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
    public void testGetBlogPostByIdWhenBlogPostExist() {
        UUID id = UUID.randomUUID();
        when(blogPostRepository.findById(id)).thenReturn(Optional.of(blogPost));
        BlogPost post = blogPostService.getBlogPostById(id);

        assertEquals(blogPost, post);
    }

    @Test
    public void testGetAllBlogPostsWithPagesWhenBlogPostsNotExists() {
        when(blogPostRepository.findAll(any(Pageable.class))).thenReturn(Page.empty());

        Page<BlogPost> blogPosts = blogPostService.getAllBlogPostsWithPages(pageable);

        assertNotNull(blogPosts);
        assertTrue(blogPosts.isEmpty());
    }

    @Test
    public void testGetAllBlogPostsWithPagesWhenBlogPostsExists() {
        Page<BlogPost> blogPosts = new PageImpl<>(List.of(new BlogPost()));
        when(blogPostRepository.findAll(any(Pageable.class))).thenReturn(blogPosts);

        Page<BlogPost> posts = blogPostService.getAllBlogPostsWithPages(pageable);

        assertNotNull(posts);
        assertFalse(posts.isEmpty());
        assertEquals(1, blogPosts.getTotalElements());
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
    void testDeleteBlogPostWhenBlogPostNotExist() {
        doThrow(IllegalArgumentException.class).when(blogPostRepository).deleteById(any(UUID.class));

        assertThrows(IllegalArgumentException.class, () -> blogPostService.deleteBlogPost(UUID.randomUUID()));
    }

    @Test
    void testDeleteBlogPostWhenBlogPostExist() {
        UUID uuid = UUID.randomUUID();
        doNothing().when(blogPostRepository).deleteById(uuid);

        blogPostService.deleteBlogPost(uuid);

        verify(blogPostRepository, times(1)).deleteById(uuid);
    }

    @Test
    void findLastFivePostWhenBlogPostNotExist() {
        when(blogPostRepository.findFirst5ByOrderByDateDesc()).thenReturn(Collections.emptyList());

        List<BlogPost> blogPosts = blogPostService.findLastFivePost();

        assertNotNull(blogPosts);
        assertTrue(blogPosts.isEmpty());
    }

    @Test
    void findLastFivePostWhenOneBlogPostExist() {
        List<BlogPost> blogPosts = List.of(new BlogPost());
        when(blogPostRepository.findFirst5ByOrderByDateDesc()).thenReturn(blogPosts);

        List<BlogPost> posts = blogPostService.findLastFivePost();

        assertNotNull(posts);
        assertFalse(posts.isEmpty());
        assertEquals(1, posts.size());
    }

    @Test
    void findAllDistinctCategoriesWhenBlogPostNotExist() {
        when(blogPostRepository.getDistinctCategoriesNative()).thenReturn(Collections.emptyList());

        List<String> categories = blogPostService.findAllDistinctCategories();

        assertNotNull(categories);
        assertTrue(categories.isEmpty());
    }

    @Test
    void findAllDistinctCategoriesWhenOneBlogPostExist() {
        when(blogPostRepository.getDistinctCategoriesNative()).thenReturn(List.of("category"));

        List<String> categories = blogPostService.findAllDistinctCategories();

        assertNotNull(categories);
        assertFalse(categories.isEmpty());
        assertEquals(1, categories.size());
        assertEquals("category", categories.get(0));
    }


    @Test
    void findBlogPostsByCategoryWhenBlogPostNotExist() {
        String category = "category";
        when(blogPostRepository.findBlogPostsByCategory(any(String.class), any(Pageable.class))).thenReturn(Page.empty());

        Page<BlogPost> blogPosts = blogPostService.findBlogPostsByCategory(category, pageable);

        assertNotNull(blogPosts);
        assertTrue(blogPosts.isEmpty());
    }

    @Test
    void findBlogPostByCategoryWhenBlogPostExist(){
        String category = "Sport";
        BlogPost testBlogPost = new BlogPost();
        testBlogPost.setCategory(category);
        Page<BlogPost> blogPosts = new PageImpl<>(List.of(testBlogPost));
        when(blogPostRepository.findBlogPostsByCategory(category, pageable)).thenReturn(blogPosts);

        Page<BlogPost> posts = blogPostService.findBlogPostsByCategory(category, pageable);

        assertNotNull(posts);
        assertFalse(posts.isEmpty());
        assertEquals(1, posts.getTotalElements());
        assertEquals("Sport", posts.get().collect(Collectors.toList()).get(0).getCategory());
    }
}
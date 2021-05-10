package lt.codeacademy.project.blog.controller;

import lt.codeacademy.project.blog.model.BlogPost;
import lt.codeacademy.project.blog.service.BlogPostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.View;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class BlogPostControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BlogPostService blogPostService;

    @Mock
    private View view;

    @InjectMocks
    private BlogPostController blogPostController;

    @BeforeEach
    void setUp() {
        mockMvc = standaloneSetup(blogPostController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setSingleView(view)
                .build();
    }

    @Test
    void testGetBlogPostsWhenBlogPostsNotExists() throws Exception {
        when(blogPostService.getAllBlogPostsWithPages(any(Pageable.class))).thenReturn(Page.empty());

        mockMvc.perform(get("/public/posts")
                .param("size", "10")
                .param("sort", "name"))
                .andExpect(status().isOk())
                .andExpect(view().name("posts"))
                .andExpect(model().attribute("postsPage", Page.empty()))
                .andExpect(model().attribute("lastsPosts", blogPostService.findLastFivePost()))
                .andExpect(model().attribute("categories", blogPostService.findAllDistinctCategories()));
    }

    @Test
    void testGetBlogPostsWhenOneBlogPostExist() throws Exception {
        BlogPost blogPost = new BlogPost();
        blogPost.setName("Test Name");
        blogPost.setDescription("Test description");
        blogPost.setContent("Test content");
        blogPost.setCategory("Test category");
        Page<BlogPost> blogPosts = new PageImpl<>(List.of(blogPost));

        when(blogPostService.getAllBlogPostsWithPages(any(Pageable.class))).thenReturn(blogPosts);

        mockMvc.perform(get("/public/posts")
                .param("size", "15")
                .param("sort", "name"))
                .andExpect(status().isOk())
                .andExpect(view().name("posts"))
                .andExpect(model().attribute("postsPage", blogPosts))
                .andExpect(model().attribute("lastsPosts", blogPostService.findLastFivePost()))
                .andExpect(model().attribute("categories", blogPostService.findAllDistinctCategories()));
    }

    @Test
    void testOpenCreateNewBlogPostForm() throws Exception {
        mockMvc.perform(get("/public/posts/create"))
                .andExpect(model().attributeExists("post"))
                .andExpect(status().isOk())
                .andExpect(view().name("create"));
    }

    @Test
    void testCreateNewBlogPost() throws Exception {
        mockMvc.perform(post("/public/posts/create")
                .contentType("post"))
                .andExpect(status().isOk())
                .andExpect(view().name("/public/posts"));
    }

    @Test
    void testGetPostByIdWhenBlogPostExist() throws Exception {
        UUID uuid = UUID.randomUUID();
        BlogPost blogPost = new BlogPost();
        blogPost.setId(uuid);

        when(blogPostService.getBlogPostById(eq(uuid))).thenReturn(blogPost);

        mockMvc.perform(get("/public/posts/getPost")
                .param("id", "" + blogPost.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("post"))
                .andExpect(model().attribute("post", blogPost))
                .andExpect(model().attribute("lastsPosts", blogPostService.findLastFivePost()));
    }

    @Test
    void updateBlogPost() {
    }

    @Test
    void testUpdateBlogPost() {
    }

    @Test
    void deleteBlogPost() {
    }

    @Test
    void getBlogPostsSortedByCategories() {
    }
}
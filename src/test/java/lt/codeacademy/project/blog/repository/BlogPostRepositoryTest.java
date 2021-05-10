package lt.codeacademy.project.blog.repository;

import lt.codeacademy.project.blog.model.BlogPost;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class BlogPostRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Test
    void findFirst5ByOrderByDateDesc() {
        List<BlogPost> blogPosts = blogPostRepository.findFirst5ByOrderByDateDesc();

        assertEquals(5, blogPosts.size());
    }

    @Test
    void getDistinctCategoriesNative() {
    }

    @Test
    void findBlogPostsByCategory() {
    }
}
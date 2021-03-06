package lt.codeacademy.project.blog.controller;

import lombok.extern.slf4j.Slf4j;
import lt.codeacademy.project.blog.model.BlogPost;
import lt.codeacademy.project.blog.service.BlogPostService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping("/public/posts")
@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class BlogPostController {
    private final BlogPostService blogPostService;

    public BlogPostController(@Qualifier("blogPostServiceImpl") BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @GetMapping
    public String getBlogPosts(@PageableDefault(size = 5, sort = {"name"}, direction = Sort.Direction.ASC) Pageable pageable,
                               Model model) {
        model.addAttribute("postsPage", blogPostService.getAllBlogPostsWithPages(pageable));
        model.addAttribute("lastsPosts", blogPostService.findLastFivePost());
        model.addAttribute("categories", blogPostService.findAllDistinctCategories());
        return "posts";
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String openCreateNewBlogPostForm(Model model) {
        model.addAttribute("post", new BlogPost());
        return "create";
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String createNewBlogPost(@Valid @ModelAttribute("post") BlogPost blogPost, BindingResult errors) {
        if (errors.hasErrors()) {
            return "create";
        }
        log.debug("Ceated new post" + blogPost);
        blogPostService.addBlogPost(blogPost);
        return "redirect:/public/posts";
    }

    @GetMapping("/getPost")
    public String getPostById(@RequestParam UUID id, Model model) {
        BlogPost blogPostst = blogPostService.getBlogPostById(id);

        model.addAttribute("post", blogPostst);
        model.addAttribute("lastsPosts", blogPostService.findLastFivePost());
        return "post";
    }

    @GetMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateBlogPost(@RequestParam UUID id, Model model) {
        BlogPost blogPostst = blogPostService.getBlogPostById(id);
        model.addAttribute("post", blogPostst);
        return "create";
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateBlogPost(@Valid @ModelAttribute("post") BlogPost blogPost, BindingResult errors) {
        if (errors.hasErrors()) {
            return "create";
        }
        log.debug("Updeted post " + blogPost);
        blogPostService.updateBlogPost(blogPost);
        return "redirect:/public/posts";
    }

    @GetMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteBlogPost(@RequestParam UUID id) {
        blogPostService.deleteBlogPost(id);
        return "redirect:/public/posts";
    }

    @GetMapping("/sortByCategories")
    public String getBlogPostsSortedByCategories(@RequestParam String postCategory,
                                                 @PageableDefault(size = 5, sort = {"name"},
                                                         direction = Sort.Direction.ASC)
                                                         Pageable pageable,
                                                 Model model) {
        model.addAttribute("postsPage", blogPostService.findBlogPostsByCategory(postCategory, pageable));
        model.addAttribute("lastsPosts", blogPostService.findLastFivePost());
        model.addAttribute("categories", blogPostService.findAllDistinctCategories());
        model.addAttribute("postCategory", postCategory);
        return "posts";
    }
}

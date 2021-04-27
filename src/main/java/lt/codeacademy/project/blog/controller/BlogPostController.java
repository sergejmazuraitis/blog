package lt.codeacademy.project.blog.controller;

import lombok.extern.slf4j.Slf4j;
import lt.codeacademy.project.blog.model.BlogPost;
import lt.codeacademy.project.blog.service.BlogPostService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping("/posts")
@Slf4j
public class BlogPostController {
    private final BlogPostService blogPostService;

    public BlogPostController(@Qualifier("blogPostServiceImpl") BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @GetMapping("/findByName")
    public String getBlogPostByName(@RequestParam String name, Model model) {

        model.addAttribute("post", blogPostService.getBlogPostByName(name));
        return "post";
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
    public String openCreateNewBlogPostForm(Model model) {
        model.addAttribute("post", new BlogPost());
        return "create";
    }

    @PostMapping("/create")
    public String createNewBlogPost(@Valid @ModelAttribute("post") BlogPost blogPost, BindingResult errors) {
        if (errors.hasErrors()) {
            return "create";
        }
        log.debug("Ceated new post" + blogPost);
        blogPostService.addBlogPost(blogPost);
        return "redirect:/posts";
    }

    @GetMapping("/getPost")
    public String getPostById(@RequestParam UUID id, Model model) {
        BlogPost blogPostst = blogPostService.getBlogPostById(id);
        model.addAttribute("post", blogPostst);
        model.addAttribute("lastsPosts", blogPostService.findLastFivePost());
        return "post";
    }

    @GetMapping("/update")
    public String updateBlogPost(@RequestParam UUID id, Model model) {
        BlogPost blogPostst = blogPostService.getBlogPostById(id);
        model.addAttribute("post", blogPostst);
        return "create";
    }

    @PostMapping("/update")
    public String updateBlogPost(@Valid @ModelAttribute("post") BlogPost blogPost, BindingResult errors) {
        if (errors.hasErrors()) {
            return "create";
        }
        log.debug("Updeted post " + blogPost);
        blogPostService.updateBlogPost(blogPost);
        return "redirect:/posts";
    }

    @GetMapping("/delete")
    public String deleteBlogPost(@RequestParam UUID id) {
        blogPostService.deleteBlogPost(id);
        return "redirect:/posts";
    }

    @GetMapping("/sortByCategories")
    public String getBlogPostsSortedByCategories(@RequestParam String category,
                                                 @PageableDefault(size = 5, sort = {"name"},
                                                         direction = Sort.Direction.ASC)
                                                         Pageable pageable,
                                                 Model model) {
        model.addAttribute("postsPage", blogPostService.findBlogPostsByCategory(category, pageable));
        model.addAttribute("lastsPosts", blogPostService.findLastFivePost());
        model.addAttribute("categories", blogPostService.findAllDistinctCategories());
        return "posts";
    }
}

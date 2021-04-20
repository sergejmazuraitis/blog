package lt.codeacademy.project.blog.controller;

import lt.codeacademy.project.blog.model.BlogPost;
import lt.codeacademy.project.blog.service.BlogPostService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequestMapping("/posts")
public class BlogPostController {
    private final BlogPostService blogPostService;

    public BlogPostController(@Qualifier("blogPostServiceImpl") BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @GetMapping("/findByName")
    public String getBlogPostByName(@RequestParam String name, Model model){

        model.addAttribute("post", blogPostService.getBlogPostByName(name));
        return "post";
    }

    @GetMapping
    public String getBlogPosts(@PageableDefault(size = 5) Pageable pageable, Model model){
        model.addAttribute("postsPage", blogPostService.getAllBlogPostsWithPages(pageable));
        return "posts";
    }

    @GetMapping("/create")
    public String openCreateNewBlogPostForm(Model model){
        model.addAttribute("post", new BlogPost());
        return "create";
    }

    @PostMapping("/create")
    public String createNewBlogPost(BlogPost blogPost){
        blogPostService.addBlogPost(blogPost);
        return "redirect:/posts";
    }

    @GetMapping("/getPost")
    public String getPostById(@RequestParam UUID id, Model model){
        BlogPost blogPostst = blogPostService.getBlogPostById(id);
        model.addAttribute("post", blogPostst);
        return "post";
    }

    @GetMapping("/update")
    public String updateBlogPost(@RequestParam UUID id, Model model){
        BlogPost blogPostst = blogPostService.getBlogPostById(id);
        model.addAttribute("post", blogPostst);
        return "create";
    }

    @PostMapping("/update")
    public String updateBlogPost(BlogPost blogPostst){
        blogPostService.updateBlogPost(blogPostst);
        return "redirect:/posts";
    }

    @GetMapping("/delete")
    public String deleteBlogPost(@RequestParam UUID id){
        blogPostService.deleteBlogPost(id);
        return "redirect:/posts";
    }
}

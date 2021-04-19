package lt.codeacademy.project.blog.controller;

import lt.codeacademy.project.blog.model.Post;
import lt.codeacademy.project.blog.service.PostService;
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
public class PostController {
    private final PostService postService;

    public PostController(@Qualifier("postServiceImpl") PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/findByName")
    public String getProductByName(@RequestParam String name, Model model){

        model.addAttribute("post", postService.getPostByName(name));
        return "post";
    }

    @GetMapping
    public String getPosts(@PageableDefault(size = 5) Pageable pageable, Model model){
        model.addAttribute("postsPage", postService.getAllPostsWithPages(pageable));
        return "posts";
    }

    @GetMapping("/create")
    public String openCreateNewPostForm(Model model){
        model.addAttribute("post", new Post());
        return "create";
    }

    @PostMapping("/create")
    public String createNewPost(Post post){
        postService.addPost(post);
        return "redirect:/posts";
    }

    @GetMapping("/getPost")
    public String getPostById(@RequestParam UUID id, Model model){
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "post";
    }

    @GetMapping("/update")
    public String updatePost(@RequestParam UUID id, Model model){
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "create";
    }

    @PostMapping("/update")
    public String updatePost(Post post){
        postService.updatePost(post);
        return "redirect:/posts";
    }

    @GetMapping("/delete")
    public String deletePost(@RequestParam UUID id){
        postService.deletePost(id);
        return "redirect:/posts";
    }
}

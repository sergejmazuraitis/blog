package lt.codeacademy.project.blog.controller;

import lt.codeacademy.project.blog.service.PostService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String getPosts(Model model){
        model.addAttribute("postsPage", postService.getPosts());
        return "posts";
    }
}

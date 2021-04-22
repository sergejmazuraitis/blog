package lt.codeacademy.project.blog.controller;

import lt.codeacademy.project.blog.model.Comment;
import lt.codeacademy.project.blog.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public String getCommentsByBlogPostId(@RequestParam UUID id, Model model) {
        model.addAttribute("comments", commentService.getCommentsByBlogPostId(id));
        return "comments";
    }

    @GetMapping("/create")
    public String openCreateNewCommentForm(@RequestParam UUID id, Model model) {
        Comment comment = new Comment();
        comment.setBlogPostId(id);
        model.addAttribute("comment", comment);
        return "createcomment";
    }

    @PostMapping("/create")
    public String createNewCommentForm(@Valid Comment comment, BindingResult errors) {
        if (errors.hasErrors()) {
            return "createcomment";
        }
        commentService.addComment(comment);
        return "redirect:/posts";
    }

    @GetMapping("/update")
    public String updateComment(@RequestParam UUID id, Model model) {
        Comment comment = commentService.getCommentById(id);
        model.addAttribute("comment", comment);
        return "createcomment";
    }

    @PostMapping("/update")
    public String updateComment(@Valid Comment comment, BindingResult errors) {
        if (errors.hasErrors()) {
            return "createcomment";
        }
        commentService.updateComment(comment);
        return "redirect:/posts";
    }

    @GetMapping("/delete")
    public String deleteComment(@RequestParam UUID id) {
        commentService.deleteComment(id);
        return "redirect:/posts";
    }

}

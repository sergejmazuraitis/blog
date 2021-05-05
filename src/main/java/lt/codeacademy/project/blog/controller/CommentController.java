package lt.codeacademy.project.blog.controller;

import lombok.extern.slf4j.Slf4j;
import lt.codeacademy.project.blog.model.Comment;
import lt.codeacademy.project.blog.service.BlogPostService;
import lt.codeacademy.project.blog.service.CommentService;
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
@Slf4j
public class CommentController {
    private final CommentService commentService;
    private final BlogPostService blogPostService;

    public CommentController(CommentService commentService, BlogPostService blogPostService) {
        this.commentService = commentService;
        this.blogPostService = blogPostService;
    }

    @GetMapping("/public/comments")
    public String getCommentsByBlogPostId(@RequestParam UUID id,
                                          Model model,
                                          @PageableDefault(size = 1, sort = {"date"}, direction = Sort.Direction.ASC) Pageable pageable) {
        model.addAttribute("comments", commentService.getCommentsByBlogPostId(id, pageable));
        model.addAttribute("lastsPosts", blogPostService.findLastFivePost());
        model.addAttribute("id", id);

        return "comments";
    }

    @GetMapping("/private/comments/create")
    public String openCreateNewCommentForm(@RequestParam UUID id, Model model) {
        Comment comment = new Comment();
        comment.setBlogPost(blogPostService.getBlogPostById(id));
        model.addAttribute("comment", comment);
        return "createcomment";
    }

    @PostMapping("/create")
    public String createNewCommentForm(@Valid Comment comment, BindingResult errors) {
        if (errors.hasErrors()) {
            return "createcomment";
        }

        log.debug("Created new comment " + comment);
        commentService.addComment(comment);
        return "redirect:/public/posts";
    }

    @GetMapping("/private/comments/update")
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
        log.debug("Updated comment " + comment);
        commentService.updateComment(comment);
        return "redirect:/public/posts";
    }

    @GetMapping("/private/comments/delete")
    public String deleteComment(@RequestParam UUID id) {
        commentService.deleteComment(id);
        return "redirect:/public/posts";
    }
}

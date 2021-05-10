package lt.codeacademy.project.blog.controller;

import lombok.extern.slf4j.Slf4j;
import lt.codeacademy.project.blog.model.Comment;
import lt.codeacademy.project.blog.service.BlogPostService;
import lt.codeacademy.project.blog.service.CommentService;
import lt.codeacademy.project.blog.service.UserRegistryService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Slf4j
@RequestMapping("/public/comments")
public class CommentController {
    private final CommentService commentService;
    private final BlogPostService blogPostService;
    private final UserRegistryService userRegistryService;

    public CommentController(CommentService commentService,
                             BlogPostService blogPostService,
                             UserRegistryService userRegistryService) {
        this.commentService = commentService;
        this.blogPostService = blogPostService;
        this.userRegistryService = userRegistryService;
    }

    @GetMapping
    public String getCommentsByBlogPostId(@RequestParam UUID id,
                                          Model model,
                                          @PageableDefault(size = 15, sort = {"date"},
                                                  direction = Sort.Direction.ASC) Pageable pageable) {
        model.addAttribute("comments", commentService.getCommentsByBlogPostId(id, pageable));
        model.addAttribute("lastsPosts", blogPostService.findLastFivePost());
        model.addAttribute("id", id);

        return "comments";
    }

    @GetMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String openCreateNewCommentForm(@RequestParam UUID id,
                                           @RequestParam UUID userId,
                                           Model model) {
        Comment comment = new Comment();
        comment.setBlogPost(blogPostService.getBlogPostById(id));
        comment.setUser(userRegistryService.getUserById(userId));
        model.addAttribute("comment", comment);
        return "createcomment";
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String createNewCommentForm(@Valid Comment comment,
                                       BindingResult errors) {
        if (errors.hasErrors()) {
            return "createcomment";
        }

        log.debug("Created new comment " + comment);
        commentService.addComment(comment);
        return "redirect:/public/posts";
    }

    @GetMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String updateComment(@RequestParam UUID id,
                                @RequestParam UUID userId,
                                @RequestParam String role,
                                Model model) {

        if (!commentService.validateIsUserComment(id, userId, role)) {
            return "/public/posts";
        }
        Comment comment = commentService.getCommentById(id);
        model.addAttribute("comment", comment);
        return "createcomment";
    }

    @PostMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String updateComment(@Valid Comment comment,
                                BindingResult errors) {
        if (errors.hasErrors()) {
            return "createcomment";
        }
        log.debug("Updated comment " + comment);
        commentService.updateComment(comment);
        return "redirect:/public/posts";
    }

    @GetMapping("/delete")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String deleteComment(@RequestParam UUID id,
                                @RequestParam UUID userId,
                                @RequestParam String role) {
        if (!commentService.validateIsUserComment(id, userId, role)) {
            return "/public/posts";
        }
        commentService.deleteComment(id);
        return "redirect:/public/posts";
    }
}

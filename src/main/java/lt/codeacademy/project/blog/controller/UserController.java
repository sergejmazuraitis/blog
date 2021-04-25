package lt.codeacademy.project.blog.controller;

import lombok.extern.slf4j.Slf4j;
import lt.codeacademy.project.blog.model.User;
import lt.codeacademy.project.blog.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String openUserRegiretForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String createNewUser(@Valid User user, BindingResult errors) {
        if (errors.hasErrors()) {
            return "registration";
        }
        log.debug("Create new user: " + user);
        userService.createNewUser(user);
        return "redirect:/posts";
    }
}

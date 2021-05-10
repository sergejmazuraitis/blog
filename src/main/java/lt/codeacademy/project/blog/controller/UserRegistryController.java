package lt.codeacademy.project.blog.controller;

import lt.codeacademy.project.blog.model.User;
import lt.codeacademy.project.blog.service.UserRegistryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/public/register")
public class UserRegistryController {
    private final UserRegistryService userRegistryService;

    public UserRegistryController(UserRegistryService userRegistryService) {
        this.userRegistryService = userRegistryService;
    }

    @GetMapping
    public String openRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping
    public String registerNewUser(@Valid User user, BindingResult errors) {
        if (errors.hasErrors()) {
            return "registration";
        }
        userRegistryService.addUser(user);
        return "redirect:/blogLogin";
    }
}

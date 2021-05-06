package lt.codeacademy.project.blog.service;

import lt.codeacademy.project.blog.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserRegistryService {
    void addUser(User user);
}

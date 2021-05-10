package lt.codeacademy.project.blog.service;

import lt.codeacademy.project.blog.model.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface UserRegistryService {
    void addUser(User user);

    User getUserById(UUID id);
}

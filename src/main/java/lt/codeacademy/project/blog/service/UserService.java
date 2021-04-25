package lt.codeacademy.project.blog.service;

import lt.codeacademy.project.blog.model.User;

import java.util.UUID;

public interface UserService {
    User getUserById(UUID userId);
    void createNewUser(User user);
    User getUserByUserNameAndPassword(String userName, String password);
}

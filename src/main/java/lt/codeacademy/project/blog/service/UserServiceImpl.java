package lt.codeacademy.project.blog.service;

import lt.codeacademy.project.blog.exception.UserFoundException;
import lt.codeacademy.project.blog.model.User;
import lt.codeacademy.project.blog.repository.UserRepository;

import java.util.UUID;

public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(UserFoundException::new);
    }

    @Override
    public void createNewUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserByIdAndUsername(UUID userId, String username) {
        return userRepository.getUserByUserIdAndUserName(userId, username);
    }
}

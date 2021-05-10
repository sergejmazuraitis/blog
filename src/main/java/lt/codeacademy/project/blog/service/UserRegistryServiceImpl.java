package lt.codeacademy.project.blog.service;

import lombok.extern.slf4j.Slf4j;
import lt.codeacademy.project.blog.exception.RoleFoundExeption;
import lt.codeacademy.project.blog.exception.UserFoundException;
import lt.codeacademy.project.blog.model.Role;
import lt.codeacademy.project.blog.model.User;
import lt.codeacademy.project.blog.repository.RoleRepository;
import lt.codeacademy.project.blog.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class UserRegistryServiceImpl implements UserRegistryService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public UserRegistryServiceImpl(UserRepository userRepository,
                                   RoleRepository roleRepository,
                                   PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void addUser(User user) {
        try {
            String encodedPassword = passwordEncoder.encode(user.getPassword()).trim();
            user.setPassword(encodedPassword);
            user.setRepeatPassword(encodedPassword);
            Role role = roleRepository.findByName("USER").orElseThrow(RoleFoundExeption::new);
            user.addRole(role);
            userRepository.save(user);
        } catch (IllegalArgumentException e) {
            log.error("Cannot create User {}", user);
        }
    }

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(UserFoundException::new);
    }

}

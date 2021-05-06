package lt.codeacademy.project.blog.service;

import lt.codeacademy.project.blog.model.Role;
import lt.codeacademy.project.blog.model.User;
import lt.codeacademy.project.blog.repository.RoleRepository;
import lt.codeacademy.project.blog.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistryServiceImpl implements UserRegistryService{
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
        String encodedPassword = passwordEncoder.encode(user.getPassword()).trim();
        user.setPassword(encodedPassword);
        user.setRepeatPassword(encodedPassword);
        Role role = roleRepository.findByName("USER").orElseThrow(NullPointerException::new);
        user.addRole(role);
        userRepository.save(user);
    }
}

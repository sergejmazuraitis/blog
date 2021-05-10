package lt.codeacademy.project.blog.service;

import lt.codeacademy.project.blog.exception.UserFoundException;
import lt.codeacademy.project.blog.model.User;
import lt.codeacademy.project.blog.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testLoadUserByUsernameWhenUserNameNotExist() {
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.ofNullable(null));

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("Aga"));

    }

    @Test
    void testLoadUserByUsernameWhenUserNameExist() {
        User user = new User();
        String username = "nick";
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDetails testUser = userService.loadUserByUsername(username);

        assertEquals(user, testUser);
    }
}
package lt.codeacademy.project.blog.service;

import lt.codeacademy.project.blog.exception.UserFoundException;
import lt.codeacademy.project.blog.model.User;
import lt.codeacademy.project.blog.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRegistryServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private User user;

    @InjectMocks
    private UserRegistryServiceImpl userRegistryService;

    @Test
    void testGetUserByIdWhenUserNotExist() {
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(null));

        assertThrows(UserFoundException.class, () -> userRegistryService.getUserById(UUID.randomUUID()));
    }

    @Test
    void testGetUserByIdWhenUserExist() {
        UUID uuid = UUID.randomUUID();
        when(userRepository.findById(uuid)).thenReturn(Optional.of(user));

        User testUser = userRegistryService.getUserById(uuid);

        assertEquals(user, testUser);
    }

}
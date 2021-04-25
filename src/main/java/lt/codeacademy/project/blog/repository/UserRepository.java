package lt.codeacademy.project.blog.repository;

import lt.codeacademy.project.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User getUserByUserIdAndUserName(UUID userId, String username);
}

package lt.codeacademy.project.blog.service;

import lt.codeacademy.project.blog.model.Role;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface RoleService {
   Optional<Role> getRoleByName(String name);
}

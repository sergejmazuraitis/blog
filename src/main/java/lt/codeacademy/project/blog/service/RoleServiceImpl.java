package lt.codeacademy.project.blog.service;

import lt.codeacademy.project.blog.model.Role;
import lt.codeacademy.project.blog.repository.RoleRepository;

import java.util.Optional;

public class RoleServiceImpl implements RoleService{
    private final RoleRepository repository;

    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Role> getRoleByName(String name) {
        return repository.findByName(name);
    }
}

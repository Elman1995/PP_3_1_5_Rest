package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    public List<Role> getRole();

    public Optional<Role> getRole(Long id);

    public void save(Role role);

    public void update(Long id, Role updatedRole);

    public void delete(Long id);

    public Role findByName(String name);

}

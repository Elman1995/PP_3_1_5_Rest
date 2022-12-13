package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.configs.PasswordEncoderConfig;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final PasswordEncoderConfig passwordEncoderConfig;
    private final UserRepository userRepository;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(PasswordEncoderConfig passwordEncoderConfig, UserRepository userRepository, RoleService roleService) {
        this.passwordEncoderConfig = passwordEncoderConfig;
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    public List<User> getUser() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public void save(User user) {
        Role roleUser = roleService.findByName("ROLE_USER");
        user.addRoles(roleUser);
        user.setPassword(passwordEncoderConfig.getPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(Long id, User updatedUser) {
        Optional<User> findUser = getUser(id);

        if (findUser.isEmpty()) {
            throw new EntityNotFoundException();
        }

        User user = findUser.get();
        user.setUsername(updatedUser.getUsername());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(passwordEncoderConfig.getPasswordEncoder().encode(updatedUser.getPassword()));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<User> findUser = getUser(id);

        if (findUser.isEmpty()) {
            throw new EntityNotFoundException();
        }

        User user = findUser.get();
        for (Role role : user.getRoles()) {
            user.deleteRoles(role);
        }

        userRepository.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}

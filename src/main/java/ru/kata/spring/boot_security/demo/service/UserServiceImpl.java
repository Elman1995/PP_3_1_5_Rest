package ru.kata.spring.boot_security.demo.service;

import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.configs.PasswordEncoderConfig;
import ru.kata.spring.boot_security.demo.exceptions.UserException;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleService roleService) {
        this.passwordEncoder = passwordEncoder;
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
        if (userRepository.findByUsername(user.getUsername()) != null && userRepository.findByUsername(user.getUsername()).getId() != user.getId()) {
            throw new UserException(user);
        }

        if (user.getId() != null && userRepository.findById(user.getId()).get().getPassword().equals(user.getPassword())) {
            user.setPassword(userRepository.findById(user.getId()).get().getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userRepository.save(user);
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

    @PostConstruct
    public User createTestAdmin() {

        Role roleUser = roleService.findByName("ROLE_USER");
        if (roleUser == null) {
            roleUser = new Role(1L,"ROLE_USER");
            roleService.save(roleUser);
        }

        Role roleAdmin = roleService.findByName("ROLE_ADMIN");
        if (roleAdmin == null) {
            roleAdmin = new Role(2L,"ROLE_ADMIN");
            roleService.save(roleAdmin);
        }

        if (findByUsername("admin") == null) {

            User user = new User("admin", "admin", "admin@mail.com", new BCryptPasswordEncoder().encode("1234"));

            Long id = 1L;
            Optional<User> findUser = getUser(id);
            while (!findUser.isEmpty()) {
                id++;
                findUser = getUser(id);
            }

            user.setId(id);
            user.addRoles(roleUser);
            user.addRoles(roleAdmin);
            return userRepository.save(user);
        }

        return null;

    }

}

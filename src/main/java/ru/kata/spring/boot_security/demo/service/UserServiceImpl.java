package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> getUser() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {

        Optional<User> findUser = userRepository.findById(id);

        if (findUser.isEmpty()) {
            throw new EntityNotFoundException();
        }

        return findUser.get();
    }

    @Transactional
    public void save(User user) {

        Role roleUser = roleRepository.findByName("ROLE_USER");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.addRoles(roleUser);
        userRepository.save(user);
    }

    @Transactional
    public void update(Long id, User updatedUser) {

        Optional<User> findUser = userRepository.findById(id);

        if (findUser.isEmpty()) {
            throw new EntityNotFoundException();
        }

        User user = findUser.get();
        user.setUsername(updatedUser.getUsername());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found !");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    @PostConstruct
    public User createTestAdmin() {

        Role roleUser = roleRepository.findByName("ROLE_USER");
        if (roleUser == null) {
            roleUser = new Role(1L,"ROLE_USER");
            roleRepository.save(roleUser);
        }

        Role roleAdmin = roleRepository.findByName("ROLE_ADMIN");
        if (roleAdmin == null) {
            roleAdmin = new Role(2L,"ROLE_ADMIN");
            roleRepository.save(roleAdmin);
        }

        if (userRepository.findByUsername("admin") == null) {

            User user = new User("admin", "admin", "admin@mail.com", passwordEncoder.encode("1234"));

            Long id = 1L;
            Optional<User> findUser = userRepository.findById(id);
            while (!findUser.isEmpty()) {
                id++;
                findUser = userRepository.findById(id);
            }

            user.setId(id);
            user.addRoles(roleUser);
            user.addRoles(roleAdmin);

            return userRepository.save(user);
        }
        else {
            User user = userRepository.findByUsername("admin");
            Collection<Role> roles = user.getRoles();
            return null;
        }
    }

}

package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.UsersDetails;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public List<User> getUser();

    public User getUser(Long id);

    public void save(User user);

    public void update(Long id, User updatedUser);

    public void delete(Long id);

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}

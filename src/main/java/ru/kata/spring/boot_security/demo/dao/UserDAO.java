package ru.kata.spring.boot_security.demo.dao;



import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {

    public List<User> getUser();

    public User getUser(Long id);

    public void save(User user);

    public void update(Long id, User updatedUser);

    public void delete(Long id);

    Optional<User> findByUsername(String username);

}

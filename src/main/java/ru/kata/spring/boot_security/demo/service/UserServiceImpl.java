package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.UsersDetails;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Transactional(readOnly = true)
    public List<User> getUser() {
        return userDAO.getUser();
    }

    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return userDAO.getUser(id);
    }

    public void save(User user) {
        userDAO.save(user);
    }

    public void update(Long id, User updatedUser) {
        userDAO.update(id, updatedUser);
    }

    public void delete(Long id) {
        userDAO.delete(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userDAO.findByUsername(username);

        if (user.isEmpty())
            throw new UsernameNotFoundException("User not found!");

        return new UsersDetails(user.get());

    }
}

package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;


@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<User> getUser() {
        List<User> user = entityManager.createQuery("SELECT c FROM User c").getResultList();
        return user;
    }

    public User getUser(Long id) {
        return entityManager.find(User.class, id);
    }

    public void save(User user) {
        entityManager.persist(user);
    }

    public void update(Long id, User updatedUser) {
        User user = entityManager.find(User.class, id);
        user.setUsername(updatedUser.getUsername());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());
        entityManager.merge(user);
    }

    public void delete(Long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {

        Optional<User> user = null;

        Query query = entityManager.createQuery("SELECT c FROM User c where c.username = :username");
        query.setParameter("username", username);
        List<User> userResult = query.getResultList();

        if (!userResult.isEmpty()) {
            user = Optional.ofNullable(userResult.get(0));
        }
        else {
            user = Optional.empty();
        }

        return user;
    }

}

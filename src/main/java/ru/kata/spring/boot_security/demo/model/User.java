package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User implements UserDetails {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов длиной")
   @Column(name = "username")
   private String username;

   @Size(min = 2, max = 100, message = "Фамилия должна быть от 2 до 100 символов длиной")
   @Column(name = "last_name")
   private String lastName;

   @NotEmpty(message = "Почта не должна быть пустым")
   @Size(min = 2, max = 100, message = "Почта должна быть от 2 до 100 символов длиной")
   @Column(name = "email")
   private String email;

   @NotEmpty(message = "Пароль не должно быть пустым")
   @Size(min = 2, max = 100, message = "Пароль должен быть от 2 до 100 символов длиной")
   @Column(name = "password")
   private String password;

   @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
   private Collection<Role> roles = new HashSet<>();

   public User() {}

   public User(String username, String lastName, String email, String password, Collection<Role> roles) {
      this.username = username;
      this.lastName = lastName;
      this.email = email;
      this.password = password;
      this.roles = roles;
   }

   public User(String username, String lastName, String email, String password) {
      this.username = username;
      this.lastName = lastName;
      this.email = email;
      this.password = password;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public Collection<Role> getRoles() {
      return roles;
   }

   public void setRoles(Collection<Role> roles) {
      this.roles = roles;
   }

   public void addRoles(Role role) {
      this.roles.add(role);
   }

   @Override
   public String toString() {
      return "User{" +
              "id=" + id +
              ", username='" + username + '\'' +
              ", lastName='" + lastName + '\'' +
              ", email='" + email + '\'' +
              ", password='" + password + '\'' +
              ", roles=" + roles +
              '}';
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return roles;
   }

   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return true;
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, username, lastName, email, password, roles);
   }

   @Override
   public boolean equals(Object obj) {

      if (this == obj) return true;

      if (obj == null || getClass() != obj.getClass()) return false;

      User user = (User) obj;

      return id == user.id
              && Objects.equals(username, user.username)
              && Objects.equals(lastName, user.lastName)
              && Objects.equals(email, user.email)
              && Objects.equals(password, user.password)
              && Objects.equals(roles, user.roles);

   }
}

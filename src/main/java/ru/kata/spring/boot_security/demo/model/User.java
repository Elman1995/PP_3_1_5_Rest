package ru.kata.spring.boot_security.demo.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user")
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;

   @NotEmpty(message = "Имя не должно быть пустым")
   @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов длиной")
   @Column(name = "username")
   private String username;

   @NotEmpty(message = "Фамилие не должно быть пустым")
   @Size(min = 2, max = 100, message = "Фамилие должно быть от 2 до 100 символов длиной")
   @Column(name = "last_name")
   private String lastName;

   @NotEmpty(message = "Почта не должна быть пустым")
   @Size(min = 2, max = 100, message = "Почта должна быть от 2 до 100 символов длиной")
   @Column(name = "email")
   private String email;

   @Column(name = "password")
   private String password;

   public User() {}

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

   public void setUsername(String firstName) {
      this.username = firstName;
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

   @Override
   public String toString() {
      return "User{" +
              "id=" + id +
              ", firstName='" + username + '\'' +
              ", lastName='" + lastName + '\'' +
              ", email='" + email + '\'' +
              ", password='" + password + '\'' +
              '}';
   }
}

package com.desarrolloweb.tallerspringboot.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(nullable = false, length = 100)
  @NotEmpty(message = "El nombre no puede estar vacío")
  private String full_name;

  @Column(nullable = false, length = 100, unique = true)
  @NotEmpty(message = "El nombre de usuario no puede estar vacío")
  private String username;

  @Column(nullable = false, length = 500)
  @NotEmpty(message = "La contraseña no puede estar vacía")
  private String password;

  public Usuario() {
    
  }
  
  public Usuario(String full_name, String username, String password) {
    this.full_name = full_name;
    this.username = username;
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFull_name() {
    return full_name;
  }

  public void setFull_name(String full_name) {
    this.full_name = full_name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "{\"id\":\"" + String.valueOf(id) + "\", \"full_name\":\"" + full_name + "\", \"username\":\"" + username + "\"}";
  }
}

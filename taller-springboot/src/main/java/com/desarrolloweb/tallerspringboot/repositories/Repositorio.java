package com.desarrolloweb.tallerspringboot.repositories;

import org.springframework.data.repository.CrudRepository;

import com.desarrolloweb.tallerspringboot.models.Usuario;

public interface Repositorio extends CrudRepository<Usuario, Long> {
 
  public Usuario findByUsername(String username);
  
}

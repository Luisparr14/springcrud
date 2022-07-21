package com.desarrolloweb.tallerspringboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desarrolloweb.tallerspringboot.models.Usuario;
import com.desarrolloweb.tallerspringboot.repositories.Repositorio;

@Service
public class UsuarioService implements IUsuarioService {

  @Autowired
  private Repositorio repositorio;

  @Override
  public void guardar(Usuario usuario) {
    repositorio.save(usuario);
  }

  @Override
  public void eliminar(Long id) {
    repositorio.deleteById(id);
  }

  public Usuario buscarPorId(Long id) {
    Usuario usuario = null;
    try {
      usuario = repositorio.findById(id).get();
    } catch (Exception e) {
      System.out.println(e);
    }
    return usuario;
  }

  @Override
  public List<Usuario> buscarTodos() {
    return (List<Usuario>) repositorio.findAll();
  }

  @Override
  public Usuario buscarPorUsuario(String username) {
    Usuario usuario = null;
    try {
      usuario = repositorio.findByUsername(username);
    } catch (Exception e) {
      System.out.println(e);
    }
    return usuario;
  }

  @Override
  public Boolean existeUsuario(String username) {
    Usuario usuario = buscarPorUsuario(username);
    if (usuario == null) {
      return false;
    } else {
      return true;
    }
  }

  @Override
  public Boolean existeId(Long id) {
    Usuario usuario = buscarPorId(id);
    if (usuario == null) {
      return false;
    } else {
      return true;
    }
  }
}

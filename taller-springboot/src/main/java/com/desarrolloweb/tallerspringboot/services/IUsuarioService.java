package com.desarrolloweb.tallerspringboot.services;

import java.util.List;

import com.desarrolloweb.tallerspringboot.models.Usuario;

public interface IUsuarioService {
  void guardar(Usuario usuario);
  void eliminar(Long id);
  Usuario buscarPorId(Long id);
  List<Usuario> buscarTodos();
  Usuario buscarPorUsuario(String username);
  Boolean existeUsuario(String username);
  Boolean existeId(Long id);
}
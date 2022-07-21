package com.desarrolloweb.tallerspringboot.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.desarrolloweb.tallerspringboot.models.Usuario;
import com.desarrolloweb.tallerspringboot.services.IUsuarioService;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RequestMapping(value = "/api", produces = "application/json")
public class RestControllerApi {

  @Autowired
  private IUsuarioService usuarioService;

  @GetMapping("/listar")
  public Iterable<Usuario> profile() {
    try {
      List<Usuario> usuarios = usuarioService.buscarTodos();
      return usuarios;
    } catch (Exception e) {
      System.out.println(e);
    }
    return null;
  }

  @PostMapping("/login")
  public Object procesarLogin(@RequestBody Usuario usuario, HttpServletResponse res) {
    HashMap<String, Object> response = new HashMap<>();
    try {      
      Usuario usuarioEncontrado = null;
      usuarioEncontrado = usuarioService.buscarPorUsuario(usuario.getUsername());      
      if (usuarioEncontrado != null) {
        if (usuarioEncontrado.getPassword().equals(usuario.getPassword())) {
          response.put("ok", true);
          response.put("usuario", usuarioEncontrado);
          response.put("mensaje", "Login exitoso");
        } else {
          res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          response.put("mensaje", "Usuario o contraseña incorrecta");
          response.put("ok", false);
        }
        return response;
      } else {
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.put("mensaje", "Usuario o contraseña incorrecta");
        response.put("ok", false);
        return response;
      }
    } catch (Exception e) {
      response.put("ok", false);
      response.put("mensaje", e);
      return response;
    }
  }

  @PostMapping("/register")
  public ResponseEntity<Usuario> procesarRegistro(@Valid @RequestBody Usuario usuario, BindingResult result) {
    try {
      System.out.println("Usuario: " + usuario);
      if (result.hasErrors()) {
        return ResponseEntity.badRequest().body(null);
      }
      usuarioService.guardar(usuario);
      return ResponseEntity.created(null).body(usuario);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  @DeleteMapping("/eliminar/{id}")
  public ResponseEntity<Boolean> eliminar(@PathVariable("id") Long id) {
    try {
        usuarioService.eliminar(id);
        return ResponseEntity.ok().body(true);
    } catch (Exception e) {
      return ResponseEntity.status(404).body(false);
    }
  }

  @PutMapping("/editar/{id}")
  public ResponseEntity<Usuario> procesarEditar(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable("id") Long id) {
    try {
      Boolean existeId = usuarioService.existeId(id);
      if (result.hasErrors()) {
        return ResponseEntity.badRequest().body(null);
      }
      System.out.println("Existe id: " + existeId);
      if (existeId) {
        Usuario usuarioEncontrado = usuarioService.buscarPorId(id);
        usuarioEncontrado.setFull_name(usuario.getFull_name());
        usuarioEncontrado.setUsername(usuario.getUsername());
        usuarioEncontrado.setPassword(usuario.getPassword());
        usuarioService.guardar(usuarioEncontrado);
        return ResponseEntity.ok().body(usuarioEncontrado);
      } else {
        return ResponseEntity.status(404).body(null);
      }
    } catch (Exception e) {
      System.out.println(e);
      return ResponseEntity.internalServerError().build();
    }
  }
}

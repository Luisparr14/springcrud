package com.desarrolloweb.tallerspringboot.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.desarrolloweb.tallerspringboot.models.Usuario;
import com.desarrolloweb.tallerspringboot.repositories.Repositorio;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
  @Autowired
  private Repositorio repositorio;
  

  @GetMapping("/editar/{id}")
  public String editar(@PathVariable("id") Long id, Model model, HttpSession session) {
    try {
      if (session.getAttribute("usuario") == null) {
        return "redirect:/listar";
      } else {
        Usuario usuario = repositorio.findById(id).get();
        model.addAttribute("usuario", usuario);
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    return "usuario/editar";
  }

  @PostMapping("/editar/{id}")
  public String procesarEditar(@Valid Usuario usuario, BindingResult result, Model model, @PathVariable("id") Long id,
      HttpSession session) {
    try {
      if (result.hasErrors()) {
        return "usuario/editar";
      }
      if (session.getAttribute("usuario") == null) {
        return "redirect:/listar";
      } else {
        session.setAttribute("usuario", usuario.getUsername());
        repositorio.save(usuario);
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    return "redirect:/listar";
  }
}

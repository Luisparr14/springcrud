package com.desarrolloweb.tallerspringboot.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.desarrolloweb.tallerspringboot.models.Usuario;
import com.desarrolloweb.tallerspringboot.services.IUsuarioService;

@Controller
public class HomeController {
  
  @Autowired
  private IUsuarioService usuarioService;

  @GetMapping("/")
  public String index(Model model) {
    return "forward:/listar";
  }

  @GetMapping("/listar")
  public String profile(Model model, HttpSession session) {
    Usuario usuario = null;
    try {
      if (session.getAttribute("usuario") == null) {
        return "redirect:/login";
      } else {
        Object usuarioSession = session.getAttribute("usuario");
        usuario = usuarioService.buscarPorUsuario(usuarioSession.toString());
        if (usuario == null) {
          session.removeAttribute("usuario");
          return "redirect:/login";
        }else{
          List<Usuario> usuarios = usuarioService.buscarTodos();
          model.addAttribute("usuarios", usuarios);
          return "index";
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    return "index";
  }

  @GetMapping("/login")
  public String login(Model model, HttpSession session) {
    try {
      if (session.getAttribute("usuario") != null) {
        return "redirect:/listar";
      } else {
        Usuario usuario = new Usuario();
        model.addAttribute("usuario", usuario);
        return "login";
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    return "login";
  }

  @PostMapping("/login")
  public String procesarLogin(Usuario usuario, HttpSession session) {
    
    try {      
      Usuario usuarioEncontrado = null;
      usuarioEncontrado = usuarioService.buscarPorUsuario(usuario.getUsername());
      if (usuarioEncontrado != null) {
        if (usuarioEncontrado.getPassword().equals(usuario.getPassword())) {
          session.setAttribute("usuario", usuarioEncontrado.getUsername());
          return "redirect:/listar";
        } else {
          return "redirect:/login";
        }
      } else {
        return "redirect:/login";
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    return "login";
  }

  @GetMapping("/register")
  public String register(Model model, HttpSession session) {
    if (session.getAttribute("usuario") != null) {
      return "redirect:/listar";
    } else {
      Usuario usuario = new Usuario();
      model.addAttribute("usuario", usuario);
      return "register";
    }
  }

  @PostMapping("/register")
  public String procesarRegistro(@Valid Usuario usuario, BindingResult result, Model model) {
    Boolean existeUsuario = usuarioService.existeUsuario(usuario.getUsername());
    
    try {
      if (result.hasErrors()) {
        return "register";
      } else {
        if (existeUsuario) {
          model.addAttribute("existe", "El usuario está registrado");
          return "register";
        } else {
          usuarioService.guardar(usuario);
          return "redirect:/login";
        }
      }
    } catch (Exception e) {
      System.out.println(e);
      return "register";
    }
  }

  @RequestMapping("/*")
  public String redirect() {
    return "404";
  }
}

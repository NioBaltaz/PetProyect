package com.codingdojo.proyecto.controllers;

import java.security.Principal;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codingdojo.proyecto.models.User;
import com.codingdojo.proyecto.services.AppService;

@Controller
public class MainController {

	@Autowired
    private AppService service;
	
	@GetMapping ("/index")
	public String index() {
		return "index.jsp";
	}

    @GetMapping("/registration")
    public String register(@ModelAttribute("user") User user) {
        return "register.jsp";
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
        service.saveWithUserRole(user, result);
        if(result.hasErrors()) {
            return "register.jsp";
        }else {
            return "redirect:/login";
        }
    }

    @GetMapping("/login")
    public String login(@RequestParam(value="error", required=false) String error, Model model) {
        if(error != null) {
            model.addAttribute("errorMessage", "Credenciales inválidas, intentar nuevamente.");
        }
        return "login.jsp";
    }

    @RequestMapping(value= {"/", "/home"})
    public String home(Principal principal, Model model) {
        //Me regresa el username del usuario que inició sesión
        String username = principal.getName();
        //Obtenemos el objeto de Usuario
        User currentUser = service.findUserByUsername(username);
        //Mandamos el usuario a home.jsp
        model.addAttribute("currentUser", currentUser);

        model.addAttribute("asdasd", currentUser.getRoles());
        return "home.jsp";
    }

    @GetMapping("/administradores")
    public String administradores() {
        return "administradores.jsp";
    }
    
   
	
	
}

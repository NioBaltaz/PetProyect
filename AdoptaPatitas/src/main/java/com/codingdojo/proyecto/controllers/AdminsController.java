package com.codingdojo.proyecto.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.codingdojo.proyecto.models.Pet;
import com.codingdojo.proyecto.models.User;
import com.codingdojo.proyecto.services.AppService;

@Controller
public class AdminsController {

	@Autowired
	private AppService service;
	
	ArrayList<String> options = new ArrayList<String>();
	
	 @GetMapping("/admins")
    public String administradores() {
        return "administradores.jsp";
    }
	
	@GetMapping("/admins/new/pet")
	public String newPet(@ModelAttribute("newPet") Pet pet, Model model) {
		options.add("Si");
		options.add("No");
		model.addAttribute("options", options);
		return "newPet.jsp";
	}
	
	@PostMapping("/admins/create/pet")
	public String createPet(@Valid @ModelAttribute("newPet") Pet pet, BindingResult result, Principal principal, @RequestParam("imagen") MultipartFile imagen) {
		if(result.hasErrors()) {
			return "newPet.jsp";
		}
		else {
			//Me regresa el username del usuario que inició sesión
            String username = principal.getName();             
            //Obtenemos el objeto de Usuario
            User currentUser = service.findUserByUsername(username);
            
            if(!imagen.isEmpty()) {
            	//Ruta
            	Path directorioImagenes = Paths.get("/src/main/resources/static/img");
            	//Ruta Absoluta
            	String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
            	
            	try {
            		//Imagen en Bytes
            		byte[] bytesImg = imagen.getBytes();
            		//Ruta completa, con todo y nombre de imagen
            		Path rutaCompleta = Paths.get(rutaAbsoluta+"/"+imagen.getOriginalFilename());
            		//Guardar mi imagen en la ruta
            		Files.write(rutaCompleta, bytesImg);   
            		
            		//Nombre dentro del atributo image en Pet
            		pet.setImage(imagen.getOriginalFilename());
            		
            	}catch(IOException e){
            		e.printStackTrace();
            	}
            }
            
            service.newPet(pet, currentUser);                     
            return "redirect:/adopta";
		}
	}
}

package com.codingdojo.proyecto.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.codingdojo.proyecto.models.Form;
import com.codingdojo.proyecto.models.Option;
import com.codingdojo.proyecto.models.Pet;
import com.codingdojo.proyecto.models.Product;
import com.codingdojo.proyecto.models.User;
import com.codingdojo.proyecto.services.AppService;
import com.codingdojo.proyecto.services.SendMailService;

@Controller
public class AdminsController {

	@Autowired
	private AppService service;
	
	@Autowired
	private SendMailService sendMailService;
	
	 @GetMapping("/admins")
    public String administradores(Principal principal, Model model) {
		if(principal == null) {
	    		return "index.jsp";
    	}
    	
        //Me regresa el username del usuario que inició sesión
        String username = principal.getName();             
        //Obtenemos el objeto de Usuario
        User currentUser = service.findUserByUsername(username);              
        //Mandamos el usuario a home.jsp
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("roles", currentUser.getRoles());
		 
		List<Form> forms = service.findAllForms();
		List<Pet> pets = service.findAllPets();
		List<Product> products = service.findAllProducts();
		model.addAttribute("pets", pets);
	    model.addAttribute("forms", forms);
	    model.addAttribute("products", products);
        return "administradores.jsp";
    }
	
	@GetMapping("/admins/new/pet")
	public String newPet(@ModelAttribute("newPet") Pet pet, Model model) {
		model.addAttribute("options", Option.Options);
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
            	//Rut/aceptar/adopcion/a
            	Path directorioImagenes = Paths.get("src/main/resources/static/img");
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

	@GetMapping("/solicitud/{form_id}/{nombre_mascota}")
	public String solicitud(@PathVariable("form_id") Long form_id, @PathVariable("nombre_mascota") String nombre_mascota, Model model) {		
		Form form = service.findFormById(form_id);
		Pet pet = service.findPetByName(nombre_mascota);
		model.addAttribute("pet", pet);
		model.addAttribute("form", form);
		return "solicitud.jsp";
	}
	
	@PostMapping("/aceptar/adopcion/{pet_id}/{user_id}/{form_id}")
	public String aceptarAdopcion(@PathVariable("pet_id") Long pet_id, @PathVariable("user_id") Long user_id, @PathVariable("form_id") Long form_id, Principal principal) {
		if(principal == null) {
    		return "adopta.jsp";
    	}
		Form form = service.findFormById(form_id);
		form.setAceptado("si");
		String email_user = form.getEmail();
		String name_user = form.getNombre_adoptante();
		sendMailService.sendMail(email_user, "Solicitud Aceptada", "¡Hola " + name_user + "!, tu solicitud de adopción se ha aceptado, dentro de unos días estaremos contactandote por teléfono para agendar una cita y seguir con el proceso.");
        service.acceptRequest(pet_id, user_id);
        return "redirect:/adopta";
	}
	
	@GetMapping("/admins/add/product")
	public String addProduct(@ModelAttribute("newProduct")Product product){
		return "newProduct.jsp";
	}
	@PostMapping("/admins/add/product")
	public String createProduct(@Valid @ModelAttribute("newProduct") Product product, BindingResult result, Principal principal, @RequestParam("imagen") MultipartFile imagen) {
		if(result.hasErrors()) {
			return "newProduct.jsp";
		}
		else {
			//Me regresa el username del usuario que inició sesión
            String username = principal.getName();             
            //Obtenemos el objeto de Usuario
            User currentUser = service.findUserByUsername(username);
            
            if(!imagen.isEmpty()) {
            	//Rut/aceptar/adopcion/a
            	Path directorioImagenes = Paths.get("src/main/resources/static/img");
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
            		product.setImage(imagen.getOriginalFilename());
           
            	}catch(IOException e){
            		e.printStackTrace();
            	}
            }
            
            service.newProduct(product, currentUser);                     
            return "redirect:/tienda";
		}
	}
	
	@PostMapping("/search")
	public String search(@RequestParam(value="pet") String pet) {
		return "redirect:/search/"+pet;
	}
	
	@GetMapping("/search/{pet}")
	public String searchPet(@PathVariable("pet") String pet, Model model) {
		Pet pet_name = service.findPetByName(pet);
		model.addAttribute("petObj", pet_name);
		return "administradores.jsp";
	} 
	
	@GetMapping("/pet/{pet}")
	public String pet(@PathVariable("pet") Long thispet, Model model, @ModelAttribute("ObjectPet") Pet pet) {
		Pet thisPet = service.findPetById(thispet);
		model.addAttribute("options", Option.Options);
		model.addAttribute("pet", thisPet);
		return "pet.jsp";		
	}
	
	@PutMapping("/update/pet")
	public String updatePet(@Valid @ModelAttribute("ObjectPet") Pet pet, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("options", Option.Options);
			return "pet.jsp";
		}else {
			Pet thisPet = service.findPetById(pet.getId());
			service.updatePet(thisPet);
			return "redirect:/pet/"+pet.getId();
		}
	}
}

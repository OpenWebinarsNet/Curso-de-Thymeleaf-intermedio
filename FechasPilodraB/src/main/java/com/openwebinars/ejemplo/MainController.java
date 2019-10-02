package com.openwebinars.ejemplo;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("/")
	public String index(Model model) {
		
		model.addAttribute("persona", new Persona("Una persona", LocalDate.of(1990, 1, 1)));
		
		return "index";
	}

}

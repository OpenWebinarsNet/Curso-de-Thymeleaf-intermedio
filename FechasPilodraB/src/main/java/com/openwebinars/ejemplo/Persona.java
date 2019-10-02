package com.openwebinars.ejemplo;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class Persona {

	private String nombre;
	
	private LocalDate fecha;
	
	
}

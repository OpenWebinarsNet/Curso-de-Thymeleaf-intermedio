package com.openwebinars.proyecto.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
@Entity
public class Categoria {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String nombre;
	
	private boolean destacada;
	
	private String imagen;

	public Categoria(String nombre, boolean destacada, String imagen) {
		this.nombre = nombre;
		this.destacada = destacada;
		this.imagen = imagen;
	}
		

}

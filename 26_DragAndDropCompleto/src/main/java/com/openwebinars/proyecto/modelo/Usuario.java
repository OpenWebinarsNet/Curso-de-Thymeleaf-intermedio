package com.openwebinars.proyecto.modelo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Usuario {
	
	@Id @GeneratedValue
	private Long id;
	
	private String nombre;
	
	private String username;
	private String password;	
	private String rol;
	
	@ManyToMany(cascade = {
	        CascadeType.PERSIST,
	        CascadeType.MERGE
	    })
	@JoinTable(name = "favoritos",
		joinColumns = @JoinColumn(name = "usuario_id"),
		inverseJoinColumns = @JoinColumn(name="producto_id"))
	private Set<Producto> productos = new HashSet<>();
	
	public void addProductoFavorito(Producto p) {
		this.productos.add(p);
		p.getUsuarios().add(this);
	}

	
	public void removeProductoFavorito(Producto p) {
		p.getUsuarios().remove(this);
		this.productos.remove(p);
	}
	
}

package com.openwebinars.proyecto.modelo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class Producto {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@NotEmpty
	private String nombre;
	
	@Lob 
	private String descripcion;
	
	@Min(0)
	private float pvp;
	
	private float descuento;
	
	@URL
	private String imagen;
	
	@NotNull
	@ManyToOne
	private Categoria categoria;
	
	@OneToMany(mappedBy="producto", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
	private Set<Puntuacion> puntuaciones = new HashSet<>();

	public Producto(String nombre, String descripcion, float pvp, float descuento, String imagen, Categoria categoria) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.pvp = pvp;
		this.descuento = descuento;
		this.imagen = imagen;
		this.categoria = categoria;
	}

	
	/**
	 * Métodos helper
	 */
	public void addPuntuacion(Puntuacion puntuacion) {
		this.puntuaciones.add(puntuacion);
		puntuacion.setProducto(this);
	}
	
	
	public double getPuntuacionMedia() {
		if (this.puntuaciones.isEmpty())
			return 0;
		else 
			return this.puntuaciones.stream()
					.mapToInt(Puntuacion::getPuntuacion)
					.average()
					.getAsDouble();
	}
	
	public double getNumeroTotalPuntuaciones() {
		return this.puntuaciones.size();
	}
	

}

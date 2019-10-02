package com.openwebinars.proyecto.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	
	/*private float descuento;*/
	
	@URL
	private String imagen;
	
	@Enumerated(EnumType.STRING)
	private Disponibilidad disponibilidad;
	
	@NotNull
	@ManyToOne
	private Categoria categoria;
	
	@OneToMany(mappedBy="producto", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
	private Set<Puntuacion> puntuaciones = new HashSet<>();

	@OneToMany(mappedBy="producto", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
	private List<Oferta> ofertas = new ArrayList<>();
	
	
	/**
	 * Métodos helper
	 */
	public void addPuntuacion(Puntuacion puntuacion) {
		this.puntuaciones.add(puntuacion);
		puntuacion.setProducto(this);
	}
	
	
	public void addOferta(Oferta oferta) {
		this.ofertas.add(oferta);
		oferta.setProducto(this);
	}
	
	public void removeOferta(Oferta oferta) {
		this.ofertas.remove(oferta);
		oferta.setProducto(null);
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


	/**
	 * El método comprueba si hay alguna oferta vigente.
	 * Si la hay, devuelve el precio de oferta. Si no, el precio de venta.
	 * 
	 * @return el precio
	 */
	public float getPrecio() {
		return  (float) this.ofertas.stream()
					.filter(o -> LocalDateTime.now().isAfter(o.getFechaInicio()) && LocalDateTime.now().isBefore(o.getFechaFin()))
					.mapToDouble(Oferta::getNuevoPvp)
					.findFirst()
					.orElse(pvp);
		
	}
	

}

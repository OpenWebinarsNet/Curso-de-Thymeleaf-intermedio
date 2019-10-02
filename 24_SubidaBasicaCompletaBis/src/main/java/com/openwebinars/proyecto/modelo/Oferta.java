package com.openwebinars.proyecto.modelo;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor 
@Entity
public class Oferta {

	@Id
	@GeneratedValue
	private Long id;
	
	private String nombre;
	
	@Column(name="nuevo_pvp")
	private float nuevoPvp;
	
	@Column(name="porcentaje_descuento")
	private float porcentajeDescuento;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@Column(name="fecha_inicio")
	private LocalDateTime fechaInicio;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@Column(name="fecha_fin")	
	private LocalDateTime fechaFin;
	
	@ManyToOne
	@JoinColumn(name="producto_id")
	private Producto producto;
	
	public Oferta() {
		this.fechaInicio = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
		this.fechaFin = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).plusDays(1);
	}
	
}

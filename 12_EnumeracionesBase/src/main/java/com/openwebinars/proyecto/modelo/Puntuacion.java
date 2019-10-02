package com.openwebinars.proyecto.modelo;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Puntuacion {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@CreatedDate
	private LocalDateTime fecha;
	
	private int puntuacion;
	
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@ManyToOne
	private Producto producto;

	public Puntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}
	
	public Puntuacion(int puntuacion, Producto producto) {
		this.puntuacion = puntuacion;
		this.producto = producto;
	}


	
	

}

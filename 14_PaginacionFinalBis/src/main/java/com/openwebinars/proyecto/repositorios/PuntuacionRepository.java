package com.openwebinars.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openwebinars.proyecto.modelo.Puntuacion;

public interface PuntuacionRepository extends JpaRepository<Puntuacion, Long> {

}

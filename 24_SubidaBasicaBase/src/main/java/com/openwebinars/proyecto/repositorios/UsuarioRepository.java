package com.openwebinars.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openwebinars.proyecto.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Usuario findFirstByUsername(String username);
	

}

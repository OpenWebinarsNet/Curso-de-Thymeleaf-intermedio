package com.openwebinars.proyecto.seguridad;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openwebinars.proyecto.modelo.Usuario;
import com.openwebinars.proyecto.repositorios.UsuarioRepository;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	
	UsuarioRepository repositorio;
	
	public UserDetailsServiceImpl(UsuarioRepository repositorio) {
		this.repositorio = repositorio;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = repositorio.findFirstByUsername(username);
		
		UserBuilder builder = null;
		
		if (usuario != null) {
			builder = User.withUsername(username);
			builder.disabled(false);
			builder.password(usuario.getPassword());
			builder.authorities(new SimpleGrantedAuthority("ROLE_"+usuario.getRol()));
		} else {
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
		
		return builder.build();
	}

}
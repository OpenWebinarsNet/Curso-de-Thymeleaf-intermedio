package com.openwebinars.proyecto.configuracion;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication()
			.withUser("admin")
			.password("{noop}admin")
			.roles("ADMIN");
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().permitAll()
			.and()
			.formLogin()
				.defaultSuccessUrl("/", true)
				.permitAll()
			.and()
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")
				.permitAll();
		
		// Se deja puntualmente para poder acceder a la consola de H2
		http.csrf().disable();
        http.headers().frameOptions().disable();
			
			
	}
	
	

}

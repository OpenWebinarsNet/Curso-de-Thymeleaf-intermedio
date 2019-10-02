package com.openwebinars.proyecto.servicios;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.openwebinars.proyecto.modelo.Categoria;
import com.openwebinars.proyecto.modelo.Oferta;
import com.openwebinars.proyecto.modelo.Producto;
import com.openwebinars.proyecto.repositorios.ProductoRepository;

@Service
public class ProductoService {
	
	
	private ProductoRepository repositorio;
	
	
	public ProductoService(ProductoRepository repositorio) {
		this.repositorio = repositorio;
	}

	public List<Producto> findAll() {
		return repositorio.findAll();
	}
	
	public List<Producto> findByIds(Collection<Long> ids) {
		return repositorio.findAllById(ids);
	}
	
	public Page<Producto> findAllPaginated(Pageable pageable) {
		return repositorio.findAll(pageable);
	}
	
	public List<Producto> findAllByCategoria(Categoria categoria) {
		return repositorio.findByCategoria(categoria);
	}
	
	public List<Producto> findAllByCategoria(Long categoriaId) {
		return repositorio.findByCategoriaId(categoriaId);
	}
	
	public Producto findById(Long id) {
		return repositorio.findById(id).orElse(null);
	}
	
	public Producto save(Producto producto) {
		
		// Aplicamos algo de "lógica de negocio"
		// Revisamos si el producto no tiene ID y tiene ofertas.
		// De ser así, salvamos primero el producto y luego las ofertas
		if (producto.getId() == null && !producto.getOfertas().isEmpty()) {
			List<Oferta> ofertas = producto.getOfertas();
			producto.setOfertas(new ArrayList<Oferta>());
			Producto saved = repositorio.save(producto);
			ofertas.forEach(saved::addOferta);
			return repositorio.save(saved);
		} else {
			return repositorio.save(producto);			
		}
		
	}
	
	public Producto delete(Producto producto) {
		Producto result = findById(producto.getId());
		repositorio.delete(result);
		return result;
	}
	
	public int numeroProductosCategoria(Categoria categoria) {
		return repositorio.findNumProductosByCategoria(categoria);
	}
	
	
	/*
	 * Este método sirve para obtener un número de productos aleatorios.
	 * Lo realizamos en Java para abstraernos mejor de la base de datos
	 * concreta que vamos a usar.
	 * Algunos SGBDR nos permitirían usar la función RANDOM, y podríamos
	 * hacer esta consulta de forma nativa.
	 */
	public List<Producto> obtenerProductosAleatorios(int numero) {
		// Obtenemos los ids de todos los productos
		List<Long> listaIds = repositorio.obtenerIds();
		// Los desordenamos 
		Collections.shuffle(listaIds);
		// Nos quedamos con los N primeros, con N = numero.
		listaIds = listaIds.stream().limit(numero).collect(Collectors.toList());
		// Buscamos los productos con esos IDs y devolvemos la lista
		return repositorio.findAllById(listaIds);

	}
	

}

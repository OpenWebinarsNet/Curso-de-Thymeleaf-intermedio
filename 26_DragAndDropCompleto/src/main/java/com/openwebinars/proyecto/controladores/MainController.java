package com.openwebinars.proyecto.controladores;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.openwebinars.proyecto.modelo.Categoria;
import com.openwebinars.proyecto.modelo.Producto;
import com.openwebinars.proyecto.modelo.Usuario;
import com.openwebinars.proyecto.repositorios.UsuarioRepository;
import com.openwebinars.proyecto.servicios.CategoriaService;
import com.openwebinars.proyecto.servicios.ProductoService;

@Controller
public class MainController {

	private static final int NUM_PRODUCTOS_ALEATORIOS = 8;

	private CategoriaService categoriaService;

	private ProductoService productoService;

	private UsuarioRepository usuarioRepository;

	public MainController(CategoriaService categoriaService, ProductoService productoService,
			UsuarioRepository usuarioRepository) {
		this.categoriaService = categoriaService;
		this.productoService = productoService;
		this.usuarioRepository = usuarioRepository;
	}

	@ModelAttribute("favoritos")
	public Set<Long> favoritos() {

		Set<Long> result = new HashSet<>();

		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Usuario usuario = usuarioRepository.findFirstByUsername(username);
			if (usuario != null) {
				result = usuario.getProductos().stream().mapToLong(Producto::getId).collect(HashSet::new, HashSet::add,
						HashSet::addAll);
			}
		}
		return result;
	}

	@ModelAttribute("categorias")
	public List<Categoria> todasCategorias() {
		return categoriaService.findAll();
	}

	@GetMapping("/")
	public String index(@RequestParam(name = "idCategoria") Optional<Long> idCategoria, Model model) {

		List<Producto> productos;

		if (!idCategoria.isPresent()) {
			productos = productoService.obtenerProductosAleatorios(NUM_PRODUCTOS_ALEATORIOS);
		} else {
			productos = productoService.findAllByCategoria(idCategoria.get());
		}

		model.addAttribute("productos", productos);

		return "index";
	}

	@GetMapping("/user/favs")
	public String misFavoritos(Model model) {
		if (favoritos().isEmpty())
			return "redirect:/";
		else {
			model.addAttribute("productos", productoService.findByIds(favoritos()));
			return "index";
		}
	}

	@GetMapping("/product/{id}")
	public String showDetails(@PathVariable("id") Long id, Model model) {
		Producto p = productoService.findById(id);
		if (p != null) {
			model.addAttribute("producto", p);
			return "detail";
		}

		return "redirect:/";

	}

	@PostMapping("/product/{id}/addFav")
	public ResponseEntity<String> addProductoFavorito(@PathVariable("id") Long id) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuario = usuarioRepository.findFirstByUsername(username);
		Producto p = productoService.findById(id);

		if (p != null && usuario != null) {
			usuario.addProductoFavorito(p);
			usuarioRepository.save(usuario);
			return ResponseEntity.noContent().build();

		} else {
			return ResponseEntity.badRequest().build();
		}

	}

	@PostMapping("/product/{id}/removeFav")
	public ResponseEntity<String> removeProductoFavorito(@PathVariable("id") Long id) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuario = usuarioRepository.findFirstByUsername(username);
		Producto p = productoService.findById(id);

		if (p != null && usuario != null) {
			usuario.removeProductoFavorito(p);
			usuarioRepository.save(usuario);
			return ResponseEntity.noContent().build();

		} else {
			return ResponseEntity.badRequest().build();
		}

	}

}

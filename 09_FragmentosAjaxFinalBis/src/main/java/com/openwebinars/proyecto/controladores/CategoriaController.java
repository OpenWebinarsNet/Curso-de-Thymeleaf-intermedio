package com.openwebinars.proyecto.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.openwebinars.proyecto.modelo.Categoria;
import com.openwebinars.proyecto.servicios.CategoriaService;
import com.openwebinars.proyecto.servicios.I18nService;
import com.openwebinars.proyecto.servicios.ProductoService;

@Controller
@RequestMapping("/admin/categoria")
public class CategoriaController {

	private CategoriaService categoriaService;

	private ProductoService productoService;
	
	I18nService servicioInternacionalizacion;

	public CategoriaController(CategoriaService categoriaService, ProductoService productoService,
			I18nService servicioInternacionalizacion) {
		this.categoriaService = categoriaService;
		this.productoService = productoService;
		this.servicioInternacionalizacion = servicioInternacionalizacion;
	}

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("categorias", categoriaService.findAll());
		return "admin/list-categoria";
	}

	@GetMapping("/nueva")
	public String nuevaCategoria(Model model) {
		model.addAttribute("categoria", new Categoria());
		return "admin/form-categoria";
	}

	@PostMapping("/nueva/submit")
	public String submitNuevaCategoria(@ModelAttribute("categoria") Categoria categoria, Model model) {

		categoriaService.save(categoria);

		return "redirect:/admin/categoria/";
	}

	@GetMapping("/editar/{id}")
	public String editarCategoria(@PathVariable("id") Long id, Model model) {

		Categoria categoria = categoriaService.findById(id);

		if (categoria != null) {
			model.addAttribute("categoria", categoria);
			return "admin/form-categoria";
		} else {
			return "redirect:/admin/categoria/";
		}

	}

	@GetMapping("/borrar/{id}")
	public String borrarCategoria(@PathVariable("id") Long id, Model model) {

		Categoria categoria = categoriaService.findById(id);

		if (categoria != null) {

			if (productoService.numeroProductosCategoria(categoria) == 0) {
				categoriaService.delete(categoria);
			} else {
				return "redirect:/admin/categoria/?error=true";
			}

		}

		return "redirect:/admin/categoria/";

	}

	@GetMapping("/borrar/show/{id}")
	public String showModalBorrarCategoria(@PathVariable("id") Long id, Model model) {

		Categoria categoria = categoriaService.findById(id);
		String deleteMessage = "";
		if (categoria != null)
			deleteMessage = servicioInternacionalizacion.getMessage("categoria.borrar.mensaje",
					new Object[] { categoria.getNombre() });
		else
			return "redirect:/admin/categoria/?error=true";

		model.addAttribute("delete_url", "/admin/categoria/borrar/" + id);
		model.addAttribute("delete_title", servicioInternacionalizacion.getMessage("categoria.borrar.titulo"));
		model.addAttribute("delete_message", deleteMessage);
		return "fragments/modal::modal_borrar";

	}

}

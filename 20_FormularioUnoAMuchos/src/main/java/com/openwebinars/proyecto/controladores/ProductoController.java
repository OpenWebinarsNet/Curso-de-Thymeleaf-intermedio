package com.openwebinars.proyecto.controladores;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.openwebinars.proyecto.modelo.Categoria;
import com.openwebinars.proyecto.modelo.Oferta;
import com.openwebinars.proyecto.modelo.Producto;
import com.openwebinars.proyecto.servicios.CategoriaService;
import com.openwebinars.proyecto.servicios.I18nService;
import com.openwebinars.proyecto.servicios.ProductoService;

@Controller
@RequestMapping("/admin/producto")
public class ProductoController {
	
	private static final String AJAX_HEADER_NAME = "X-Requested-With";
    private static final String AJAX_HEADER_VALUE = "XMLHttpRequest";

	private ProductoService productoService;

	private CategoriaService categoriaService;
	
	private I18nService servicioInternacionalizacion;

	public ProductoController(ProductoService productoService, CategoriaService categoriaService, I18nService servicioInternacionalizacion) {
		this.productoService = productoService;
		this.categoriaService = categoriaService;
		this.servicioInternacionalizacion = servicioInternacionalizacion;
	}
	
	@ModelAttribute("categorias")
	public List<Categoria> todasCategorias() {
		return categoriaService.findAll();
	}

	@GetMapping("/")
	public String index(Model model, Pageable pageable) {
		model.addAttribute("productos", productoService.findAllPaginated(pageable));
		return "admin/list-producto";
	}

	@GetMapping("/nuevo")
	public String nuevaProducto(Model model) {
		model.addAttribute("producto", new Producto());
		return "admin/form-producto";
	}

	@PostMapping("/nuevo/submit")
	public String submitNuevoProducto(@Valid Producto producto, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "admin/form-producto";
		} else {
			productoService.save(producto);
			return "redirect:/admin/producto/";

		}

	}
	
	@PostMapping(path = "/nuevo/submit", params="addItem")
	public String addOfertaProducto(Producto producto, HttpServletRequest request) {

		producto.addOferta(new Oferta());
		
		if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "/admin/form-producto::#ofertas";
        } else {
            return "/admin/form-producto";
        }		
	}
	
	@PostMapping(path = "/nuevo/submit", params="removeItem")
	public String deleteOfertaProducto(Producto producto, @RequestParam("removeItem") int index, HttpServletRequest request) {

		Oferta o = producto.getOfertas().get(index);
		producto.removeOferta(o);
		
		if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "/admin/form-producto::#ofertas";
        } else {
            return "/admin/form-producto";
        }		
	}


	@GetMapping("/editar/{id}")
	public String editarProducto(@PathVariable("id") Long id, Model model) {

		Producto producto = productoService.findById(id);

		if (producto != null) {
			model.addAttribute("producto", producto);
			model.addAttribute("categorias", categoriaService.findAll());
			return "admin/form-producto";
		} else {
			return "redirect:/admin/producto/";
		}

	}

	@GetMapping("/borrar/{id}")
	public String borrarProducto(@PathVariable("id") Long id, Model model) {

		Producto producto = productoService.findById(id);

		if (producto != null) {
			productoService.delete(producto);
		}

		return "redirect:/admin/producto/";

	}
	
	@GetMapping("/borrar/show/{id}")
	public String showModalBorrarProducto(@PathVariable("id") Long id, Model model) {
		
		Producto producto = productoService.findById(id);
		String deleteMessage = "";
		if (producto != null)
			deleteMessage = servicioInternacionalizacion.getMessage("producto.borrar.mensaje", new Object[]{producto.getNombre() });
		else 
			return "redirect:/admin/producto/?error=true";
			
		model.addAttribute("delete_url", "/admin/producto/borrar/" + id);
		model.addAttribute("delete_title", servicioInternacionalizacion.getMessage("producto.borrar.titulo"));
		model.addAttribute("delete_message", deleteMessage);
		return "fragments/modal::modal_borrar";
	}

}

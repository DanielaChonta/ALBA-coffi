package com.alba_coffi.alba_coffi.controller;

import com.alba_coffi.alba_coffi.model.Producto;
import com.alba_coffi.alba_coffi.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador de VISTAS (Thymeleaf, sesiones 14 y 15).
 *
 * @Controller (sin "Rest"): lo que devuelve cada método es el NOMBRE de una plantilla HTML
 * ubicada en src/main/resources/templates. Thymeleaf la procesa en el servidor, mezcla el HTML
 * con los datos del Model y el navegador recibe una página HTML ya armada.
 *
 * Flujo: navegador -> GET /productos -> Controller -> Service (datos) -> Model -> Thymeleaf -> HTML
 */
@RequestMapping("/productos")
@Controller
public class ProductoViewController {

    private final ProductoService productoService;

    public ProductoViewController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // GET /productos -> catálogo completo
    @GetMapping
    public String mostrarProductos(Model model) {
        // Model = "mochila" que lleva datos del controlador a la plantilla.
        // En productos.html se lee como ${productos}
        model.addAttribute("productos", productoService.listar());
        return "productos"; // templates/productos.html
    }

    // GET /productos/3 -> detalle de un producto. Es el destino del botón "Ver detalles" (th:href)
    @GetMapping("/{id}")
    public String mostrarDetalle(@PathVariable Long id, Model model) {
        Optional<Producto> producto = productoService.buscarPorId(id);
        if (producto.isEmpty()) {
            return "redirect:/productos"; // si el id no existe, se vuelve al catálogo
        }
        model.addAttribute("producto", producto.get());
        return "producto-detalle"; // templates/producto-detalle.html
    }
}

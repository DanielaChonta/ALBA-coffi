package com.alba_coffi.alba_coffi.controller;

import com.alba_coffi.alba_coffi.model.Producto;
import com.alba_coffi.alba_coffi.service.CategoriaService;
import com.alba_coffi.alba_coffi.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductoViewController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    public ProductoViewController(ProductoService productoService, CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping("/productos")
    public String mostrarProductos(@RequestParam(required = false) String nombre,
                                   @RequestParam(required = false) Long categoriaId,
                                   Model model) {
        boolean hayNombre = nombre != null && !nombre.isBlank();
        boolean hayCategoria = categoriaId != null;

        List<Producto> productos;
        if (hayNombre && hayCategoria) {
            productos = productoService.buscarPorNombreYCategoria(nombre.trim(), categoriaId);
        } else if (hayNombre) {
            productos = productoService.buscarPorNombre(nombre.trim());
        } else if (hayCategoria) {
            productos = productoService.buscarPorCategoriaId(categoriaId);
        } else {
            productos = productoService.listar();
        }

        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categoriaService.listar());
        model.addAttribute("nombre", nombre);
        model.addAttribute("categoriaId", categoriaId);
        return "productos";
    }

    @GetMapping("/productos/{id}")
    public String mostrarDetalle(@PathVariable Long id, Model model) {
        Optional<Producto> producto = productoService.buscarPorId(id);
        if (producto.isEmpty()) {
            return "redirect:/productos";
        }
        model.addAttribute("producto", producto.get());
        return "producto-detalle";
    }
}

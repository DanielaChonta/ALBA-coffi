package com.alba_coffi.alba_coffi.controller;

import com.alba_coffi.alba_coffi.model.Producto;
import com.alba_coffi.alba_coffi.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;


@Controller
public class ProductoViewController {

    private final ProductoService productoService;

    public ProductoViewController(ProductoService productoService) {
        this.productoService = productoService;
    }

    
    @GetMapping("/productos")
    public String mostrarProductos(Model model) {
        
        model.addAttribute("productos", productoService.listar());
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

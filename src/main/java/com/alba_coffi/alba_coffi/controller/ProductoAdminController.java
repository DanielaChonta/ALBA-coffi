package com.alba_coffi.alba_coffi.controller;

import com.alba_coffi.alba_coffi.model.Categoria;
import com.alba_coffi.alba_coffi.model.Producto;
import com.alba_coffi.alba_coffi.model.Proveedor;
import com.alba_coffi.alba_coffi.service.CategoriaService;
import com.alba_coffi.alba_coffi.service.ProductoService;
import com.alba_coffi.alba_coffi.service.ProveedorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin/productos")
public class ProductoAdminController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;
    private final ProveedorService proveedorService;

    public ProductoAdminController(ProductoService productoService,
                                   CategoriaService categoriaService,
                                   ProveedorService proveedorService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
        this.proveedorService = proveedorService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("productos", productoService.listar());
        return "admin/productos/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        Producto producto = new Producto();
        producto.setCategoria(new Categoria());
        producto.setProveedor(new Proveedor());
        model.addAttribute("producto", producto);
        cargarListas(model);
        return "admin/productos/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Producto producto, BindingResult result, Model model) {
        validarRelaciones(producto, result);
        if (result.hasErrors()) {
            cargarListas(model);
            return "admin/productos/form";
        }
        productoService.registrar(producto);
        return "redirect:/admin/productos?ok=registrado";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Optional<Producto> producto = productoService.buscarPorId(id);
        if (producto.isEmpty()) {
            return "redirect:/admin/productos";
        }
        model.addAttribute("producto", producto.get());
        cargarListas(model);
        return "admin/productos/editar";
    }

    @PostMapping("/{id}/editar")
    public String actualizar(@PathVariable Long id,
                             @Valid @ModelAttribute Producto producto,
                             BindingResult result,
                             Model model) {
        if (productoService.buscarPorId(id).isEmpty()) {
            return "redirect:/admin/productos?error=noencontrado";
        }
        validarRelaciones(producto, result);
        if (result.hasErrors()) {
            producto.setId(id);
            cargarListas(model);
            return "admin/productos/editar";
        }
        productoService.actualizar(id, producto);
        return "redirect:/admin/productos?ok=actualizado";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        if (productoService.eliminar(id)) {
            return "redirect:/admin/productos?ok=eliminado";
        }
        return "redirect:/admin/productos?error=noencontrado";
    }

    private void cargarListas(Model model) {
        model.addAttribute("categorias", categoriaService.listar());
        model.addAttribute("proveedores", proveedorService.listar());
    }

    private void validarRelaciones(Producto producto, BindingResult result) {
        // Evita NullPointerException al volver a pintar th:field="*{categoria.id}"
        if (producto.getCategoria() == null) {
            producto.setCategoria(new Categoria());
        }
        if (producto.getProveedor() == null) {
            producto.setProveedor(new Proveedor());
        }
        // Categoría/proveedor obligatorios y existentes en la BD
        productoService.validarRelaciones(producto).forEach((campo, mensaje) -> {
            if (!result.hasFieldErrors(campo)) {
                result.rejectValue(campo, campo + ".invalido", mensaje);
            }
        });
    }
}

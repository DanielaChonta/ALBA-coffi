package com.alba_coffi.alba_coffi.controller;

import com.alba_coffi.alba_coffi.model.Categoria;
import com.alba_coffi.alba_coffi.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin/categorias")
public class CategoriaViewController {

    private final CategoriaService categoriaService;

    public CategoriaViewController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaService.listar());
        return "admin/categorias/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "admin/categorias/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Categoria categoria, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/categorias/form";
        }
        categoriaService.guardar(categoria);
        return "redirect:/admin/categorias?ok=registrado";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Optional<Categoria> categoria = categoriaService.buscarPorId(id);
        if (categoria.isEmpty()) {
            return "redirect:/admin/categorias";
        }
        model.addAttribute("categoria", categoria.get());
        return "admin/categorias/form";
    }

    @PostMapping("/{id}/editar")
    public String actualizar(@PathVariable Long id, @Valid @ModelAttribute Categoria categoria, BindingResult result) {
        if (categoriaService.buscarPorId(id).isEmpty()) {
            return "redirect:/admin/categorias?error=noencontrado";
        }
        categoria.setId(id);
        if (result.hasErrors()) {
            return "admin/categorias/form";
        }
        categoriaService.guardar(categoria);
        return "redirect:/admin/categorias?ok=actualizado";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        if (categoriaService.buscarPorId(id).isEmpty()) {
            return "redirect:/admin/categorias?error=noencontrado";
        }
        if (categoriaService.eliminar(id)) {
            return "redirect:/admin/categorias?ok=eliminado";
        }
        return "redirect:/admin/categorias?error=enuso";
    }
}

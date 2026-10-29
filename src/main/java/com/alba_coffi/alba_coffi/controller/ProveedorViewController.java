package com.alba_coffi.alba_coffi.controller;

import com.alba_coffi.alba_coffi.model.Proveedor;
import com.alba_coffi.alba_coffi.service.ProveedorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin/proveedores")
public class ProveedorViewController {

    private final ProveedorService proveedorService;

    public ProveedorViewController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("proveedores", proveedorService.listar());
        return "admin/proveedores/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("proveedor", new Proveedor());
        return "admin/proveedores/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Proveedor proveedor, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/proveedores/form";
        }
        proveedorService.guardar(proveedor);
        return "redirect:/admin/proveedores?ok=registrado";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Optional<Proveedor> proveedor = proveedorService.buscarPorId(id);
        if (proveedor.isEmpty()) {
            return "redirect:/admin/proveedores";
        }
        model.addAttribute("proveedor", proveedor.get());
        return "admin/proveedores/form";
    }

    @PostMapping("/{id}/editar")
    public String actualizar(@PathVariable Long id, @Valid @ModelAttribute Proveedor proveedor, BindingResult result) {
        if (proveedorService.buscarPorId(id).isEmpty()) {
            return "redirect:/admin/proveedores?error=noencontrado";
        }
        proveedor.setId(id);
        if (result.hasErrors()) {
            return "admin/proveedores/form";
        }
        proveedorService.guardar(proveedor);
        return "redirect:/admin/proveedores?ok=actualizado";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        if (proveedorService.buscarPorId(id).isEmpty()) {
            return "redirect:/admin/proveedores?error=noencontrado";
        }
        if (proveedorService.eliminar(id)) {
            return "redirect:/admin/proveedores?ok=eliminado";
        }
        return "redirect:/admin/proveedores?error=enuso";
    }
}

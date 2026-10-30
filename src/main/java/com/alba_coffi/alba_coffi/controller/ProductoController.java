package com.alba_coffi.alba_coffi.controller;

import com.alba_coffi.alba_coffi.model.Producto;
import com.alba_coffi.alba_coffi.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<Producto> listar() {
        return productoService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        return productoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public List<Producto> buscarPorCategoria(@RequestParam String categoria) {
        return productoService.buscarPorCategoria(categoria);
    }

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Producto producto, BindingResult result) {
        Map<String, String> errores = formatearErrores(producto, result);
        if (!errores.isEmpty()) {
            return ResponseEntity.badRequest().body(errores);
        }
        Producto creado = productoService.registrar(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                        @Valid @RequestBody Producto producto,
                                        BindingResult result) {
        Map<String, String> errores = formatearErrores(producto, result);
        if (!errores.isEmpty()) {
            return ResponseEntity.badRequest().body(errores);
        }
        Optional<Producto> actualizado = productoService.actualizar(id, producto);
        if (actualizado.isPresent()) {
            return ResponseEntity.ok(actualizado.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        if (productoService.eliminar(id)) {
            return ResponseEntity.ok("Producto eliminado correctamente");
        }
        return ResponseEntity.notFound().build();
    }

    private Map<String, String> formatearErrores(Producto producto, BindingResult result) {
        Map<String, String> errores = new LinkedHashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }
        // Categoría y proveedor: obligatorios y existentes en la BD
        productoService.validarRelaciones(producto).forEach(errores::put);
        return errores;
    }
}

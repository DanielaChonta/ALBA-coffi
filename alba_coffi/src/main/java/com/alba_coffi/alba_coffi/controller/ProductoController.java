package com.alba_coffi.alba_coffi.controller;

import com.alba_coffi.alba_coffi.model.Producto;
import com.alba_coffi.alba_coffi.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * API REST de productos (Spring Web, sesión 12).
 *
 * @RestController = @Controller + @ResponseBody: lo que devuelve cada método NO es una
 * página HTML, sino DATOS (JSON) que viajan directamente en la respuesta HTTP.
 * @RequestMapping("/api/productos") es la ruta base: todos los endpoints empiezan con ella.
 *
 * Esta clase es para sistemas/aplicaciones cliente. La página para personas está en
 * ProductoViewController (ruta /productos). Ambas usan el mismo ProductoService.
 */
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    // Inyección de dependencias por constructor: Spring entrega aquí el ProductoService
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // 1. GET /api/productos -> lista todos los productos (JSON)
    @GetMapping
    public List<Producto> listar() {
        return productoService.listar();
    }

    // 2. GET /api/productos/{id} -> busca uno. @PathVariable toma el {id} de la URL
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        return productoService.buscarPorId(id)
                .map(ResponseEntity::ok)                       // 200 si existe
                .orElse(ResponseEntity.notFound().build());    // 404 si no existe
    }

    // 3. GET /api/productos/buscar?categoria=Postres -> @RequestParam toma el dato de la URL (?clave=valor)
    @GetMapping("/buscar")
    public List<Producto> buscarPorCategoria(@RequestParam String categoria) {
        return productoService.buscarPorCategoria(categoria);
    }

    // 4. POST /api/productos -> crea un producto. @RequestBody convierte el JSON recibido en un objeto Producto
    @PostMapping
    public ResponseEntity<Producto> registrar(@RequestBody Producto producto) {
        Producto creado = productoService.registrar(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado); // 201 Created
    }

    // 5. PUT /api/productos/{id} -> actualiza un producto existente
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto productoActualizado) {
        return productoService.actualizar(id, productoActualizado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 6. DELETE /api/productos/{id} -> elimina un producto
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        if (productoService.eliminar(id)) {
            return ResponseEntity.ok("Producto eliminado correctamente");
        }
        return ResponseEntity.notFound().build();
    }
}

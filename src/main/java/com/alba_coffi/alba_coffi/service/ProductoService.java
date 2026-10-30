package com.alba_coffi.alba_coffi.service;

import com.alba_coffi.alba_coffi.model.Categoria;
import com.alba_coffi.alba_coffi.model.Producto;
import com.alba_coffi.alba_coffi.model.Proveedor;
import com.alba_coffi.alba_coffi.repository.CategoriaRepository;
import com.alba_coffi.alba_coffi.repository.ProductoRepository;
import com.alba_coffi.alba_coffi.repository.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProveedorRepository proveedorRepository;

    public ProductoService(ProductoRepository productoRepository,
                           CategoriaRepository categoriaRepository,
                           ProveedorRepository proveedorRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.proveedorRepository = proveedorRepository;
    }

    public List<Producto> listar() {
        return productoRepository.findAllByOrderByIdAsc();
    }

    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Producto> buscarPorCategoria(String categoria) {
        return productoRepository.findByCategoriaNombreIgnoreCase(categoria);
    }

    public List<Producto> buscarPorCategoriaId(Long categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId);
    }

    public List<Producto> buscarPorNombreYCategoria(String nombre, Long categoriaId) {
        return productoRepository.findByNombreContainingIgnoreCaseAndCategoriaId(nombre, categoriaId);
    }

    /**
     * Validación de relaciones entre tablas: categoría y proveedor deben venir seleccionados
     * Y existir en la base de datos. Devuelve un mapa campo -> mensaje (vacío si todo está bien).
     */
    public Map<String, String> validarRelaciones(Producto producto) {
        Map<String, String> errores = new LinkedHashMap<>();

        Long categoriaId = producto.getCategoria() == null ? null : producto.getCategoria().getId();
        if (categoriaId == null) {
            errores.put("categoria", "La categoría es obligatoria");
        } else if (!categoriaRepository.existsById(categoriaId)) {
            errores.put("categoria", "La categoría seleccionada no existe");
        }

        Long proveedorId = producto.getProveedor() == null ? null : producto.getProveedor().getId();
        if (proveedorId == null) {
            errores.put("proveedor", "El proveedor es obligatorio");
        } else if (!proveedorRepository.existsById(proveedorId)) {
            errores.put("proveedor", "El proveedor seleccionado no existe");
        }
        return errores;
    }

    /** Reemplaza categoría/proveedor (que solo traen el id) por las entidades completas de la BD. */
    private void cargarRelaciones(Producto producto) {
        Categoria categoria = categoriaRepository.findById(producto.getCategoria().getId())
                .orElseThrow(() -> new IllegalArgumentException("La categoría seleccionada no existe"));
        Proveedor proveedor = proveedorRepository.findById(producto.getProveedor().getId())
                .orElseThrow(() -> new IllegalArgumentException("El proveedor seleccionado no existe"));
        producto.setCategoria(categoria);
        producto.setProveedor(proveedor);
    }

    public Producto registrar(Producto producto) {
        producto.setId(null);
        cargarRelaciones(producto);
        return productoRepository.save(producto);
    }

    public Optional<Producto> actualizar(Long id, Producto productoActualizado) {
        if (!productoRepository.existsById(id)) {
            return Optional.empty();
        }
        productoActualizado.setId(id);
        cargarRelaciones(productoActualizado);
        return Optional.of(productoRepository.save(productoActualizado));
    }

    public boolean eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            return false;
        }
        productoRepository.deleteById(id);
        return true;
    }
}

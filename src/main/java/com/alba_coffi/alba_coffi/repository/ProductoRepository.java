package com.alba_coffi.alba_coffi.repository;

import com.alba_coffi.alba_coffi.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findAllByOrderByIdAsc();

    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    List<Producto> findByCategoriaId(Long categoriaId);

    List<Producto> findByCategoriaNombreIgnoreCase(String nombre);

    List<Producto> findByNombreContainingIgnoreCaseAndCategoriaId(String nombre, Long categoriaId);

    boolean existsByCategoriaId(Long categoriaId);

    boolean existsByProveedorId(Long proveedorId);
}

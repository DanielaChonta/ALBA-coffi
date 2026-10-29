package com.alba_coffi.alba_coffi.repository;

import com.alba_coffi.alba_coffi.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    List<Proveedor> findAllByOrderByIdAsc();
}

package com.alba_coffi.alba_coffi.service;

import com.alba_coffi.alba_coffi.model.Proveedor;
import com.alba_coffi.alba_coffi.repository.ProductoRepository;
import com.alba_coffi.alba_coffi.repository.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;
    private final ProductoRepository productoRepository;

    public ProveedorService(ProveedorRepository proveedorRepository, ProductoRepository productoRepository) {
        this.proveedorRepository = proveedorRepository;
        this.productoRepository = productoRepository;
    }

    public List<Proveedor> listar() {
        return proveedorRepository.findAllByOrderByIdAsc();
    }

    public Optional<Proveedor> buscarPorId(Long id) {
        return proveedorRepository.findById(id);
    }

    public Proveedor guardar(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public boolean eliminar(Long id) {
        if (productoRepository.existsByProveedorId(id)) {
            return false;
        }
        proveedorRepository.deleteById(id);
        return true;
    }
}

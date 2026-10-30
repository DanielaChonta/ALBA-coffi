package com.alba_coffi.alba_coffi.service;

import com.alba_coffi.alba_coffi.model.Categoria;
import com.alba_coffi.alba_coffi.repository.CategoriaRepository;
import com.alba_coffi.alba_coffi.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    public CategoriaService(CategoriaRepository categoriaRepository, ProductoRepository productoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    public List<Categoria> listar() {
        return categoriaRepository.findAllByOrderByIdAsc();
    }

    public Optional<Categoria> buscarPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    public Categoria guardar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public boolean eliminar(Long id) {
        if (productoRepository.existsByCategoriaId(id)) {
            return false;
        }
        categoriaRepository.deleteById(id);
        return true;
    }
}

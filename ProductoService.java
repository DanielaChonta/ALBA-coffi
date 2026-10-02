package com.alba_coffi.alba_coffi.service;

import com.alba_coffi.alba_coffi.model.Producto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ProductoService {

    private final List<Producto> productos = new ArrayList<>();
    private long siguienteId = 1; 

    public ProductoService() {
        
        cargar("Espresso ALBA", "Espresso puro de origen peruano, cuerpo intenso y aroma profundo.", 9.90, 20, "Cafés", "espresso.jpg");
        cargar("Cappuccino Terciopelo", "Espresso con leche vaporizada y una espuma suave y sedosa.", 12.90, 15, "Cafés", "cappuccino.jpg");
        cargar("Latte Caramel", "Espresso, leche cremosa y un toque de caramelo artesanal.", 13.50, 12, "Cafés", "latte.jpg");
        cargar("Chocolate Espresso", "Chocolate caliente con un shot de espresso.", 11.50, 10, "Bebidas calientes", "chocolate.jpg");
        cargar("Chai Latte", "Té chai especiado con leche vaporizada.", 12.00, 8, "Bebidas calientes", "chai.jpg");
        cargar("Infusión Vainilla", "Infusión aromática con notas dulces de vainilla.", 10.50, 0, "Bebidas calientes", "vainilla.jpg");
        cargar("Cold Brew", "Café de extracción fría, suave y refrescante.", 13.00, 14, "Bebidas frías", "cold.jpg");
        cargar("Frappé de Café", "Café helado batido con hielo y un toque de crema.", 14.50, 9, "Bebidas frías", "frappe.jpg");
        cargar("Cheesecake de Frutos Rojos", "Porción de tarta de queso con mermelada de frutos rojos.", 14.50, 6, "Postres", "Cheesecake.jpg");
        cargar("Brownie con Nueces", "Brownie de chocolate intenso con nueces.", 10.90, 0, "Postres", "Brownie.jpg");
        cargar("Alfajor Artesanal", "Alfajor relleno de manjar blanco, hecho en casa.", 6.50, 18, "Acompañamientos", "alfajor.jpg");
        cargar("Croissant de Mantequilla", "Croissant horneado diariamente, hojaldrado y dorado.", 7.50, 10, "Acompañamientos", "croissant.jpg");
    }

    
    private void cargar(String nombre, String descripcion, Double precio, Integer stock, String categoria, String imagen) {
        productos.add(new Producto(siguienteId++, nombre, descripcion, precio, stock, categoria, imagen));
    }

    
    public List<Producto> listar() {
        return new ArrayList<>(productos);
    }

    
    public Optional<Producto> buscarPorId(Long id) {
        return productos.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    
    public List<Producto> buscarPorCategoria(String categoria) {
        return productos.stream()
                .filter(p -> p.getCategoria().equalsIgnoreCase(categoria))
                .toList();
    }

    
    public Producto registrar(Producto producto) {
        if (producto.getId() == null) {
            producto.setId(siguienteId++);
        } else if (producto.getId() >= siguienteId) {
            siguienteId = producto.getId() + 1;
        }
        productos.add(producto);
        return producto;
    }

    
    public Optional<Producto> actualizar(Long id, Producto datos) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getId().equals(id)) {
                datos.setId(id);
                productos.set(i, datos);
                return Optional.of(datos);
            }
        }
        return Optional.empty();
    }

    
    public boolean eliminar(Long id) {
        return productos.removeIf(p -> p.getId().equals(id));
    }
}

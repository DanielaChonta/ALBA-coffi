package com.alba_coffi.alba_coffi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "proveedores")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del proveedor es obligatorio")
    @Size(min = 3, max = 80, message = "El nombre debe tener entre 3 y 80 caracteres")
    @Column(nullable = false, length = 80)
    private String nombre;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(min = 7, max = 15, message = "El teléfono debe tener entre 7 y 15 caracteres")
    @Column(nullable = false, length = 15)
    private String telefono;

    @OneToMany(mappedBy = "proveedor")
    @JsonIgnore
    private List<Producto> productos = new ArrayList<>();

    public Proveedor() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public List<Producto> getProductos() { return productos; }
    public void setProductos(List<Producto> productos) { this.productos = productos; }
}

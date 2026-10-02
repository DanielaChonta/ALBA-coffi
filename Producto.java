package com.alba_coffi.alba_coffi.model;

public class Producto {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private int stock;
    private String categoria;
    private String imagen;

    public Producto() {
    }

    public Producto(Long id, String nombre, String descripcion, Double precio, Integer stock, String categoria) {
        this(id, nombre, descripcion, precio, stock, categoria, "espresso.jpg");
    }

    public Producto(Long id, String nombre, String descripcion, Double precio, Integer stock, String categoria, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
        this.imagen = imagen;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }
}

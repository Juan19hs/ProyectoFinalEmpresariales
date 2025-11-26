package com.inventario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Entidad que representa un Producto del inventario.
 * 
 * Esta clase mapea la tabla 'productos' en la base de datos y contiene
 * toda la información relevante de cada producto. Implementa validaciones
 * básicas para garantizar la integridad de los datos.
 * 
 * @author Juanhs19
*/
@Entity
@Table(name = "productos")
public class Producto {
    
    /**
     * Identificador único del producto (clave primaria).
     * Se genera automáticamente en la base de datos usando estrategia IDENTITY.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /**
     * Código único del producto.
     * Usado para identificar rápidamente el producto en el inventario.
     * No puede ser nulo, debe ser único y tener entre 1 y 50 caracteres.
     */
    @NotBlank(message = "El código del producto no puede estar vacío")
    @Size(min = 3, max = 50, message = "El código debe tener entre 3 y 50 caracteres")
    @Column(nullable = false, length = 50, unique = true)
    private String codigo;
    
    /**
     * Nombre descriptivo del producto.
     * No puede ser nulo y debe tener mínimo 5 caracteres.
     */
    @NotBlank(message = "El nombre del producto no puede estar vacío")
    @Size(min = 5, max = 120, message = "El nombre debe tener entre 5 y 120 caracteres")
    @Column(nullable = false, length = 120)
    private String nombre;
    
    /**
     * Categoría a la que pertenece el producto.
     * Usado para clasificar productos en grupos.
     */
    @Size(max = 50, message = "La categoría no puede exceder 50 caracteres")
    @Column(length = 50)
    private String categoria;
    
    /**
     * Precio unitario del producto en la moneda local.
     * No puede ser nulo ni negativo.
     */
    @NotNull(message = "El precio del producto no puede estar vacío")
    @Min(value = 0, message = "El precio debe ser mayor que cero")
    @Column(nullable = false)
    private Double precio;
    
    /**
     * Cantidad de unidades disponibles en el inventario.
     * No puede ser nulo, indica cuántas unidades hay en stock.
     */
    @NotNull(message = "El stock no puede estar vacío")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(nullable = false)
    private Integer stock;
    
    /**
     * Estado del producto (activo o inactivo).
     * Por defecto, los nuevos productos se crean como activos.
     * Un producto inactivo no aparece en listados pero se mantiene en BD.
     */
    @Column(nullable = false)
    private Boolean activo = true;
    
    // ==================== CONSTRUCTORES ====================
    
    /**
     * Constructor sin parámetros.
     * Usado por JPA para crear instancias del modelo.
     */
    public Producto() {}
    
    /**
     * Constructor con todos los parámetros.
     * Crea una instancia completa de Producto.
     * 
     * @param id Identificador del producto
     * @param codigo Código único del producto
     * @param nombre Nombre descriptivo
     * @param categoria Categoría del producto
     * @param precio Precio unitario
     * @param stock Cantidad en inventario
     * @param activo Estado del producto
     */
    public Producto(Integer id, String codigo, String nombre, String categoria, 
                   Double precio, Integer stock, Boolean activo) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.activo = activo;
    }
    
    // ==================== GETTERS Y SETTERS ====================
    
    /**
     * Obtiene el identificador del producto.
     * @return ID del producto
     */
    public Integer getId() {
        return id;
    }
    
    /**
     * Establece el identificador del producto.
     * @param id Identificador del producto
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * Obtiene el código del producto.
     * @return Código único del producto
     */
    public String getCodigo() {
        return codigo;
    }
    
    /**
     * Establece el código del producto.
     * @param codigo Código único del producto
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    /**
     * Obtiene el nombre del producto.
     * @return Nombre descriptivo
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Establece el nombre del producto.
     * @param nombre Nombre descriptivo
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Obtiene la categoría del producto.
     * @return Categoría del producto
     */
    public String getCategoria() {
        return categoria;
    }
    
    /**
     * Establece la categoría del producto.
     * @param categoria Categoría del producto
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    /**
     * Obtiene el precio del producto.
     * @return Precio unitario
     */
    public Double getPrecio() {
        return precio;
    }
    
    /**
     * Establece el precio del producto.
     * @param precio Precio unitario
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    
    /**
     * Obtiene la cantidad en stock.
     * @return Cantidad disponible en inventario
     */
    public Integer getStock() {
        return stock;
    }
    
    /**
     * Establece la cantidad en stock.
     * @param stock Cantidad disponible en inventario
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    
    /**
     * Obtiene el estado del producto.
     * @return true si el producto está activo, false si está inactivo
     */
    public Boolean getActivo() {
        return activo;
    }
    
    /**
     * Establece el estado del producto.
     * @param activo true para activar, false para desactivar
     */
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    
    // ==================== MÉTODOS ADICIONALES ====================
    
    /**
     * Calcula el valor total del inventario para este producto.
     * Se obtiene multiplicando el precio por la cantidad en stock.
     * 
     * @return Valor total del inventario (precio * stock)
     */
    public Double getValorTotal() {
        if (this.precio != null && this.stock != null) {
            return this.precio * this.stock;
        }
        return 0.0;
    }
    
    /**
     * Verifica si el producto tiene bajo stock (menos de 10 unidades).
     * Útil para alertas de reorden.
     * 
     * @return true si el stock es menor a 10, false en caso contrario
     */
    public Boolean tieneBajoStock() {
        return this.stock != null && this.stock < 10;
    }
    
    /**
     * Retorna una representación en texto del producto.
     * Utilizado para logging y depuración.
     * 
     * @return String con información del producto
     */
    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                ", activo=" + activo +
                '}';
    }
}

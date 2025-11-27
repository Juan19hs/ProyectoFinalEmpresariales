package com.inventario.service;

import com.inventario.model.Producto;
import com.inventario.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;

/**
 * Servicio para gestionar operaciones de Producto.
 * 
 * Proporciona la lógica de negocio para operaciones CRUD (Create, Read, Update, Delete)
 * sobre productos. Este servicio valida datos, controla la lógica de negocio y
 * interactúa con el repositorio de base de datos.
 * 
 * @author Sistema de Inventario - Sexto Semestre
 * @version 1.0
 * @since 2025
 */
@Service
public class ProductoService {

    /**
     * Repositorio para acceder a la base de datos de productos.
     * Proporciona métodos para consultas y operaciones CRUD.
     */
    private final ProductoRepository repository;

    /**
     * Constructor para inyección por constructor.
     *
     * @param repository Repositorio de productos
     */
    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    /**
     * Lista todos los productos del sistema.
     * 
     * Retorna una lista de todos los productos almacenados en la base de datos.
     * 
     * @return Lista de todos los productos
     */
    public List<Producto> listarTodos() {
        return repository.findAll();
    }

    /**
     * Obtiene un producto específico por su identificador.
     * 
     * @param id Identificador del producto a buscar
     * @return Optional que contiene el producto si existe, vacío si no existe
     */
    public Optional<Producto> obtenerPorId(Integer id) {
        return repository.findById(id);
    }

    /**
     * Crea un nuevo producto en la base de datos.
     * 
     * Realiza validaciones de datos antes de guardar:
     * - Código debe tener al menos 3 caracteres
     * - Nombre debe tener al menos 5 caracteres
     * - Precio debe ser mayor a 0
     * - Código debe ser único en el sistema
     * 
     * @param p Objeto Producto a crear
     * @return El producto creado y guardado en la BD
     * @throws Exception Si alguna validación falla
     */
    public Producto crear(Producto p) throws Exception {
        // Validar código
        if (p.getCodigo() == null || p.getCodigo().trim().length() < 3) {
            throw new Exception("Código debe tener al menos 3 caracteres");
        }
        
        // Validar nombre
        if (p.getNombre() == null || p.getNombre().trim().length() < 5) {
            throw new Exception("Nombre debe tener al menos 5 caracteres");
        }
        
        // Validar precio
        if (p.getPrecio() == null || p.getPrecio() <= 0) {
            throw new Exception("Precio debe ser mayor a 0");
        }

        // Verificar que el código sea único
        if (repository.findByCodigo(p.getCodigo()).isPresent()) {
            throw new Exception("Código ya existe");
        }

        return repository.save(p);
    }

    /**
     * Actualiza un producto existente en la base de datos.
     * 
     * Busca el producto por ID y actualiza solo los campos que no sean null.
     * 
     * @param id Identificador del producto a actualizar
     * @param p Objeto Producto con los nuevos datos
     * @return El producto actualizado
     * @throws Exception Si el producto no existe
     */
    public Producto actualizar(Integer id, Producto p) throws Exception {
        Optional<Producto> existente = repository.findById(id);
        
        // Verificar que el producto exista
        if (!existente.isPresent()) {
            throw new Exception("Producto no encontrado");
        }

        Producto producto = existente.get();
        
        // Actualizar solo los campos que no sean null
        if (p.getNombre() != null) producto.setNombre(p.getNombre());
        if (p.getPrecio() != null) producto.setPrecio(p.getPrecio());
        if (p.getStock() != null) producto.setStock(p.getStock());
        if (p.getActivo() != null) producto.setActivo(p.getActivo());

        return repository.save(producto);
    }

    /**
     * Elimina un producto de la base de datos.
     * 
     * @param id Identificador del producto a eliminar
     * @throws Exception Si el producto no existe
     */
    public void eliminar(Integer id) throws Exception {
        // Verificar que el producto exista
        if (!repository.existsById(id)) {
            throw new Exception("Producto no encontrado");
        }
        
        repository.deleteById(id);
    }

    /**
     * Obtiene una lista con los productos más costosos.
     *
     * @param limite Número máximo de resultados a retornar
     * @return Lista de productos ordenados por precio descendente
     */
    public List<Producto> obtenerMasCostosos(int limite) {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "precio"))
                .stream()
                .limit(limite)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene una lista con los productos más baratos.
     *
     * @param limite Número máximo de resultados a retornar
     * @return Lista de productos ordenados por precio ascendente
     */
    public List<Producto> obtenerMasBaratos(int limite) {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "precio"))
                .stream()
                .limit(limite)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene una lista con los productos con mayor stock.
     *
     * @param limite Número máximo de resultados a retornar
     * @return Lista de productos ordenados por stock descendente
     */
    public List<Producto> obtenerMayorStock(int limite) {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "stock"))
                .stream()
                .limit(limite)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene una lista con los productos con menor stock.
     *
     * @param limite Número máximo de resultados a retornar
     * @return Lista de productos ordenados por stock ascendente
     */
    public List<Producto> obtenerMenorStock(int limite) {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "stock"))
                .stream()
                .limit(limite)
                .collect(Collectors.toList());
    }
}

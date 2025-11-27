package com.inventario.service;

import com.inventario.model.Categoria;
import com.inventario.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestionar operaciones sobre Categoría.
 *
 * Contiene métodos CRUD básicos que serán usados por el controlador
 * de administración. Permite listar, crear, actualizar y eliminar
 * categorías. Todas las excepciones lanzadas son simples para mantener
 * la lógica adecuada en un entorno académico.
 */
@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    /**
     * Lista todas las categorías existentes.
     *
     * @return Lista de categorías
     */
    public List<Categoria> listarTodos() {
        return repository.findAll();
    }

    /**
     * Obtiene una categoría por su ID.
     *
     * @param id Identificador de la categoría
     * @return Optional con la categoría si existe
     */
    public Optional<Categoria> obtenerPorId(Integer id) {
        return repository.findById(id);
    }

    /**
     * Crea una nueva categoría.
     *
     * @param c Categoría a crear
     * @return Categoría creada
     * @throws Exception Si ya existe una categoría con el mismo nombre
     */
    public Categoria crear(Categoria c) throws Exception {
        if (c.getNombre() == null || c.getNombre().trim().length() < 3) {
            throw new Exception("Nombre inválido para la categoría");
        }
        if (repository.findByNombre(c.getNombre()).isPresent()) {
            throw new Exception("Ya existe una categoría con ese nombre");
        }
        return repository.save(c);
    }

    /**
     * Actualiza una categoría existente.
     *
     * @param id ID de la categoría a actualizar
     * @param c Datos de la categoría para actualizar
     * @return Categoría actualizada
     * @throws Exception Si no existe la categoría
     */
    public Categoria actualizar(Integer id, Categoria c) throws Exception {
        Optional<Categoria> existente = repository.findById(id);
        if (!existente.isPresent()) {
            throw new Exception("Categoría no encontrada");
        }
        Categoria cat = existente.get();
        if (c.getNombre() != null) cat.setNombre(c.getNombre());
        if (c.getDescripcion() != null) cat.setDescripcion(c.getDescripcion());
        return repository.save(cat);
    }

    /**
     * Elimina una categoría por su ID.
     *
     * @param id ID de la categoría a eliminar
     * @throws Exception Si la categoría no existe
     */
    public void eliminar(Integer id) throws Exception {
        if (!repository.existsById(id)) {
            throw new Exception("Categoría no encontrada");
        }
        repository.deleteById(id);
    }
}

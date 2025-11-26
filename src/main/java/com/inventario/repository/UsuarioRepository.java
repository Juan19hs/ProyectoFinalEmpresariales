package com.inventario.repository;

import com.inventario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repositorio para la entidad Usuario.
 * 
 * Proporciona métodos para realizar operaciones CRUD (Create, Read, Update, Delete)
 * sobre la tabla de usuarios. Extiende JpaRepository que proporciona métodos básicos,
 * y agregamos consultas personalizadas necesarias para la autenticación.
 * 
 * @author Sistema de Inventario - Sexto Semestre
 * @version 1.0
 * @since 2025
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    /**
     * Busca un usuario por su nombre de usuario.
     * Este método es esencial para la autenticación en Spring Security.
     * 
     * @param username Nombre de usuario a buscar
     * @return Optional que contiene el usuario si existe, vacío si no existe
     */
    Optional<Usuario> findByUsername(String username);
    
    /**
     * Busca un usuario por su email.
     * Utilizado para verificar que no existan emails duplicados al registrar usuarios.
     * 
     * @param email Email a buscar
     * @return Optional que contiene el usuario si existe, vacío si no existe
     */
    Optional<Usuario> findByEmail(String email);
    
    /**
     * Verifica si existe un usuario con el email especificado.
     * 
     * @param email Email a verificar
     * @return true si existe, false si no existe
     */
    boolean existsByEmail(String email);
    
    /**
     * Verifica si existe un usuario con el nombre especificado.
     * 
     * @param username Nombre de usuario a verificar
     * @return true si existe, false si no existe
     */
    boolean existsByUsername(String username);
}

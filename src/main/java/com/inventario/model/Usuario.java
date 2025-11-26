package com.inventario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Entidad que representa un Usuario del sistema.
 * 
 * Esta clase mapea la tabla 'usuarios' en la base de datos y contiene
 * información de autenticación y perfil de usuario. Los usuarios pueden
 * acceder al sistema y gestionar productos según su rol.
 * 
 * @author Sistema de Inventario - Sexto Semestre
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "usuarios")
public class Usuario {
    
    /**
     * Identificador único del usuario (clave primaria).
     * Se genera automáticamente en la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /**
     * Nombre de usuario único para acceso al sistema.
     * No puede ser nulo ni vacío, mínimo 4 caracteres.
     */
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(min = 4, max = 50, message = "El usuario debe tener entre 4 y 50 caracteres")
    @Column(nullable = false, length = 50, unique = true)
    private String username;
    
    /**
     * Correo electrónico del usuario.
     * Debe tener formato válido de email y ser único en el sistema.
     */
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Debe proporcionar un email válido")
    @Column(nullable = false, length = 100, unique = true)
    private String email;
    
    /**
     * Contraseña encriptada del usuario.
     * Se almacena en la base de datos de forma encriptada usando BCrypt.
     */
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 4, message = "La contraseña debe tener al menos 4 caracteres")
    @Column(nullable = false)
    private String password;
    
    /**
     * Nombre completo del usuario.
     * Campo opcional para identificar al usuario de forma legible.
     */
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    @Column(length = 100)
    private String nombreCompleto;
    
    /**
     * Estado del usuario (activo o inactivo).
     * Por defecto, los nuevos usuarios se crean activos.
     * Un usuario inactivo no podrá acceder al sistema.
     */
    @Column(nullable = false)
    private Boolean activo = true;
    
    /**
     * Rol del usuario en el sistema.
     * Valores posibles:
     * - "ROLE_ADMIN": Acceso total al sistema
     * - "ROLE_USER": Acceso limitado a gestión de productos
     */
    @NotBlank(message = "El rol no puede estar vacío")
    @Column(nullable = false, length = 50)
    private String rol = "ROLE_USER";
    
    // ==================== CONSTRUCTORES ====================
    
    /**
     * Constructor sin parámetros.
     * Usado por JPA para crear instancias del modelo.
     */
    public Usuario() {}
    
    /**
     * Constructor con parámetros principales.
     * Inicializa un usuario con los datos básicos requeridos.
     * 
     * @param username Nombre de usuario único
     * @param email Email del usuario
     * @param password Contraseña (será encriptada por Spring Security)
     * @param nombreCompleto Nombre completo del usuario
     */
    public Usuario(String username, String email, String password, String nombreCompleto) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
        this.rol = "ROLE_USER";
        this.activo = true;
    }
    
    // ==================== GETTERS Y SETTERS ====================
    
    /**
     * Obtiene el identificador del usuario.
     * @return ID del usuario
     */
    public Integer getId() {
        return id;
    }
    
    /**
     * Establece el identificador del usuario.
     * @param id Identificador del usuario
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * Obtiene el nombre de usuario.
     * @return Nombre de usuario único
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Establece el nombre de usuario.
     * @param username Nombre de usuario único
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * Obtiene el email del usuario.
     * @return Email del usuario
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Establece el email del usuario.
     * @param email Email del usuario
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Obtiene la contraseña del usuario (encriptada).
     * @return Contraseña encriptada
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Establece la contraseña del usuario.
     * @param password Contraseña (será encriptada por Spring Security)
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Obtiene el nombre completo del usuario.
     * @return Nombre completo
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    
    /**
     * Establece el nombre completo del usuario.
     * @param nombreCompleto Nombre completo del usuario
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    
    /**
     * Verifica si el usuario está activo.
     * @return true si el usuario está activo, false si está inactivo
     */
    public Boolean getActivo() {
        return activo;
    }
    
    /**
     * Establece el estado del usuario.
     * @param activo true para activar, false para inactivar
     */
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    
    /**
     * Obtiene el rol del usuario.
     * @return Rol del usuario (ROLE_ADMIN o ROLE_USER)
     */
    public String getRol() {
        return rol;
    }
    
    /**
     * Establece el rol del usuario.
     * @param rol Rol del usuario (ROLE_ADMIN o ROLE_USER)
     */
    public void setRol(String rol) {
        this.rol = rol;
    }
    
    // ==================== MÉTODOS ADICIONALES ====================
    
    /**
     * Retorna una representación en texto del usuario.
     * Utilizado para logging y depuración.
     * 
     * @return String con información del usuario
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", activo=" + activo +
                ", rol='" + rol + '\'' +
                '}';
    }
}

package com.inventario.config;

import com.inventario.model.Usuario;
import com.inventario.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Componente de inicialización para crear usuarios de prueba.
 * 
 * Se ejecuta automáticamente cuando la aplicación inicia. Crea usuarios
 * de prueba si no existen, con contraseñas encriptadas usando BCrypt.
 * 
 * Usuarios creados:
 * - admin / admin123 (Rol: ROLE_ADMIN)
 * - user / user123 (Rol: ROLE_USER)
 * 
 * @author Sistema de Inventario - Sexto Semestre
 * @version 1.0
 * @since 2025
 */
@Component
public class ComponenteInicializacion implements CommandLineRunner {
    
    /**
     * Repositorio de usuarios para acceder a la base de datos.
     */
    private final UsuarioRepository repositorioUsuarios;
    
    /**
     * Codificador de contraseñas para encriptar con BCrypt.
     */
    private final PasswordEncoder codificadorContrasenas;
    
    private final Logger registrador = LoggerFactory.getLogger(ComponenteInicializacion.class);

    /**
     * Constructor para inyección por constructor.
     *
     * @param repositorioUsuarios Repositorio de usuarios
     * @param codificadorContrasenas   Codificador de contraseñas (BCrypt)
     */
    public ComponenteInicializacion(UsuarioRepository repositorioUsuarios, PasswordEncoder codificadorContrasenas) {
        this.repositorioUsuarios = repositorioUsuarios;
        this.codificadorContrasenas = codificadorContrasenas;
    }
    
    /**
     * Método ejecutado al iniciar la aplicación.
     * 
     * Crea usuarios de prueba si no existen en la base de datos.
     * Las contraseñas se encriptan automáticamente usando BCrypt.
     * 
     * @param args Argumentos de línea de comandos (no utilizados)
     * @throws Exception Si hay error al acceder a la base de datos
     */
    @Override
    public void run(String... args) throws Exception {
        // Verificar si el usuario admin ya existe
        if (!repositorioUsuarios.existsByUsername("admin")) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setEmail("admin@inventario.com");
            // Encriptar la contraseña
            admin.setPassword(codificadorContrasenas.encode("admin123"));
            admin.setNombreCompleto("Usuario Administrador");
            admin.setActivo(true);
            admin.setRol("ROLE_ADMIN");
            
            repositorioUsuarios.save(admin);
            registrador.info("Usuario admin creado exitosamente");
        }
        
        // Verificar si el usuario user ya existe
        if (!repositorioUsuarios.existsByUsername("user")) {
            Usuario user = new Usuario();
            user.setUsername("user");
            user.setEmail("user@inventario.com");
            // Encriptar la contraseña
            user.setPassword(codificadorContrasenas.encode("user123"));
            user.setNombreCompleto("Usuario Estándar");
            user.setActivo(true);
            user.setRol("ROLE_USER");
            
            repositorioUsuarios.save(user);
            registrador.info("Usuario user creado exitosamente");
        }
        
        registrador.info("Inicialización completada");
    }
}

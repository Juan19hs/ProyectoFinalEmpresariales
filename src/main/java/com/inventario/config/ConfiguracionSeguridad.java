package com.inventario.config;

import com.inventario.service.ServicioDetallesUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de Spring Security para el sistema.
 * 
 * Esta clase configura los aspectos de seguridad de la aplicación:
 * - Autenticación: verifica que los usuarios sean quiénes dicen ser
 * - Autorización: controla qué recursos puede acceder cada usuario
 * - Cifrado: encripta las contraseñas con BCrypt
 * 
 * @author Sistema de Inventario - Sexto Semestre
 * @version 1.0
 * @since 2025
 */
@Configuration
@EnableWebSecurity
public class ConfiguracionSeguridad {
    
    /**
     * Servicio personalizado para cargar detalles del usuario desde la BD.
     */
    @Autowired
    private ServicioDetallesUsuario servicioDetallesUsuario;

    /**
     * Constructor para inyección por constructor del servicio de detalles.
     *
     * @param servicioDetallesUsuario Servicio personalizado de detalles de usuario
     */
    public ConfiguracionSeguridad(ServicioDetallesUsuario servicioDetallesUsuario) {
        this.servicioDetallesUsuario = servicioDetallesUsuario;
    }
    
    /**
     * Configura el proveedor de autenticación.
     * 
     * Usa DAO (acceso a base de datos) para obtener usuarios y compara
     * contraseñas usando BCrypt. Este es el método estándar de autenticación.
     * 
     * @return DaoAuthenticationProvider configurado
     */
    @Bean
    public DaoAuthenticationProvider proveedorAutenticacion() {
        DaoAuthenticationProvider proveedorAuth = new DaoAuthenticationProvider();
        proveedorAuth.setUserDetailsService(servicioDetallesUsuario);
        proveedorAuth.setPasswordEncoder(codificadorContrasena());
        return proveedorAuth;
    }
    
    /**
     * Define el gestor de autenticación.
     * 
     * Es responsable de procesar las solicitudes de autenticación.
     * 
     * @param configuracionAutenticacion Configuración de autenticación
     * @return AuthenticationManager configurado
     * @throws Exception Si hay error en la configuración
     */
    @Bean
    public AuthenticationManager gestorAutenticacion(AuthenticationConfiguration configuracionAutenticacion) 
            throws Exception {
        return configuracionAutenticacion.getAuthenticationManager();
    }
    
    /**
     * Define el codificador de contraseñas.
     * 
     * Usa BCrypt, que es el estándar de la industria para cifrar contraseñas.
     * BCrypt es lento a propósito para dificultar ataques de fuerza bruta.
     * 
     * @return PasswordEncoder que usa BCrypt
     */
    @Bean
    public PasswordEncoder codificadorContrasena() {
        return new BCryptPasswordEncoder();
    }
    
    /**
     * Configura las reglas de seguridad de HTTP.
     * 
     * Define:
     * - Qué URLs requieren autenticación
     * - Qué URLs son públicas
     * - Dónde redirigir en caso de no autenticado
     * - Cómo hacer logout
     * 
     * @param http Constructor de seguridad HTTP
     * @return Cadena de filtros de seguridad
     * @throws Exception Si hay error en la configuración
     */
    @Bean
    public SecurityFilterChain cadenaFiltrosSeguridad(HttpSecurity http) throws Exception {
        http
            // ==================== AUTORIZACIÓN ====================
            .authorizeHttpRequests((authz) -> authz
                // Rutas públicas (sin autenticación requerida)
                .requestMatchers("/", "/login", "/css/**", "/js/**", "/images/**").permitAll()
                // Todas las demás rutas requieren autenticación
                .anyRequest().authenticated()
            )
            // ==================== LOGIN ====================
            .formLogin((form) -> form
                // URL del formulario de login
                .loginPage("/login")
                // URL donde se envía el formulario (Spring Security lo procesa automáticamente)
                .loginProcessingUrl("/login")
                // URL a donde redirigir después de login exitoso
                .defaultSuccessUrl("/productos", true)
                // URL a donde redirigir después de login fallido
                .failureUrl("/login?error")
                // Permitir que se acceda al formulario sin autenticación
                .permitAll()
            )
            // ==================== LOGOUT ====================
            .logout((logout) -> logout
                // URL para hacer logout
                .logoutUrl("/logout")
                // URL a donde redirigir después de logout
                .logoutSuccessUrl("/login?logout")
                // Limpiar sesión
                .invalidateHttpSession(true)
                // Eliminar cookies
                .clearAuthentication(true)
                // Permitir logout
                .permitAll()
            )
            // ==================== CSRF ====================
            // CSRF (Cross-Site Request Forgery) está habilitado por defecto
            // Thymeleaf añade automáticamente el token CSRF en formularios
            .csrf((csrf) -> csrf.disable()); // Deshabilitado para desarrollo simplificado
        
        return http.build();
    }
}

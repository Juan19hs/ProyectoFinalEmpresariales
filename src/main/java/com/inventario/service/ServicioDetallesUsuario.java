package com.inventario.service;

import com.inventario.model.Usuario;
import com.inventario.repository.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

/**
 * Servicio que carga los detalles de un usuario para Spring Security.
 *
 * Implementa UserDetailsService (método requerido `loadUserByUsername`).
 * Además provee métodos en español para buscar usuarios por nombre o email.
 * 
 * @author Sistema de Inventario - Sexto Semestre
 * @version 1.0
 * @since 2025
 */
@Service
public class ServicioDetallesUsuario implements UserDetailsService {

    /**
     * Repositorio de usuarios para acceder a la base de datos.
     */
    private final UsuarioRepository usuarioRepositorio;

    /**
     * Constructor para inyección por constructor del repositorio.
     * 
     * @param usuarioRepositorio Repositorio de usuarios
     */
    public ServicioDetallesUsuario(UsuarioRepository usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    /**
     * Método requerido por Spring Security para cargar un usuario por nombre.
     * Este método es llamado durante el proceso de autenticación.
     *
     * @param username Nombre de usuario
     * @return UserDetails con la información de autenticación y roles
     * @throws UsernameNotFoundException si no se encuentra o está inactivo
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOpt = usuarioRepositorio.findByUsername(username);

        if (!usuarioOpt.isPresent()) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        Usuario usuario = usuarioOpt.get();

        if (!Boolean.TRUE.equals(usuario.getActivo())) {
            throw new UsernameNotFoundException("Usuario inactivo: " + username);
        }

        GrantedAuthority autoridad = new SimpleGrantedAuthority(usuario.getRol());

        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                Collections.singleton(autoridad)
        );
    }

    /**
     * Método en español que devuelve el Optional<Usuario> por nombre.
     *
     * @param nombreUsuario Nombre de usuario a buscar
     * @return Optional que contiene el usuario si existe
     */
    public Optional<Usuario> cargarPorNombreUsuario(String nombreUsuario) {
        return usuarioRepositorio.findByUsername(nombreUsuario);
    }
    
    /**
     * Busca un usuario por su correo electrónico.
     *
     * @param correo Correo electrónico del usuario
     * @return Optional que contiene el usuario si existe
     */
    public Optional<Usuario> cargarPorCorreo(String correo) {
        return usuarioRepositorio.findByEmail(correo);
    }
}

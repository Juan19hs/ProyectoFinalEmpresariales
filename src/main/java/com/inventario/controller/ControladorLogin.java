package com.inventario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controlador para gestionar el login y logout del sistema.
 * 
 * Maneja las solicitudes GET para mostrar la página de login y
 * procesa parámetros de error o logout para mostrar mensajes al usuario.
 * 
 * @author Sistema de Inventario - Sexto Semestre
 * @version 1.0
 * @since 2025
 */
@Controller
public class ControladorLogin {
    
    /**
     * Redirige la raíz de la aplicación a la página de login o productos.
     * 
     * @return Redirección a /login
     */
    @GetMapping("/")
    public String redirigirRaiz() {
        return "redirect:/login";
    }
    
    /**
     * Muestra la página de login.
     * 
     * Maneja los parámetros opcionales de error y logout para mostrar
     * mensajes informativos al usuario.
     * 
     * @param error Parámetro opcional que indica si hubo error de autenticación
     * @param logout Parámetro opcional que indica si se realizó logout
     * @param modelo Modelo para pasar datos a la vista Thymeleaf
     * @return El nombre de la plantilla HTML a renderizar ("login")
     */
    @GetMapping("/login")
    public String mostrarLogin(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model modelo) {
        
        // Si hay parámetro de error, mostrar mensaje de error
        if (error != null) {
            modelo.addAttribute("mensaje", "Usuario o contraseña incorrectos");
            modelo.addAttribute("tipo", "error");
        }
        
        // Si hay parámetro de logout, mostrar mensaje de despedida
        if (logout != null) {
            modelo.addAttribute("mensaje", "Ha cerrado sesión correctamente");
            modelo.addAttribute("tipo", "success");
        }
        
        return "login";
    }
}

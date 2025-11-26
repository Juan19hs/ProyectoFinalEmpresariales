package com.inventario.controller;

import com.inventario.model.Producto;
import com.inventario.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para gestionar productos del sistema.
 * 
 * Maneja todas las solicitudes HTTP relacionadas con productos:
 * - Listar productos
 * - Crear nuevos productos
 * - Editar productos existentes
 * - Eliminar productos
 * 
 * Todos los métodos requieren autenticación (protegidos por Spring Security).
 * 
 * @author Sistema de Inventario - Sexto Semestre
 * @version 1.0
 * @since 2025
 */
@Controller
@RequestMapping("/productos")
public class ProductoController {

    /**
     * Servicio para realizar operaciones CRUD sobre productos.
     */
    private final ProductoService service;

    /**
     * Constructor para inyección de dependencias (recomendado en Spring).
     * Se usa inyección por constructor para facilitar pruebas y evitar
     * problemas de inicialización.
     *
     * @param service Servicio de productos
     */
    public ProductoController(ProductoService service) {
        this.service = service;
    }

    /**
     * Lista todos los productos del sistema.
     * 
     * Obtiene la lista completa de productos de la base de datos
     * y los envía a la vista HTML para su visualización.
     * 
     * @param model Modelo de Spring para pasar datos a la vista Thymeleaf
     * @return El nombre de la plantilla HTML ("productos/listado")
     */
    @GetMapping
    public String listar(Model model) {
        List<Producto> productos = service.listarTodos();
        model.addAttribute("productos", productos);
        return "productos/listado";
    }

    /**
     * Muestra el formulario para crear un nuevo producto.
     * 
     * Proporciona un objeto Producto vacío al formulario para que
     * el usuario pueda rellenarlo.
     * 
     * @param model Modelo de Spring para pasar datos a la vista
     * @return El nombre de la plantilla HTML ("productos/formulario")
     */
    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        model.addAttribute("producto", new Producto());
        return "productos/formulario";
    }

    /**
     * Crea un nuevo producto en la base de datos.
     * 
     * Procesa el envío del formulario de nuevo producto, realiza
     * validaciones y guarda el producto en la BD. Si hay error,
     * muestra un mensaje de error al usuario.
     * 
     * @param producto Objeto Producto con los datos del formulario
     * @param redirect Atributos para redirigir con mensajes flash
     * @return Redirección a la lista de productos
     */
    @PostMapping
    public String crear(@ModelAttribute Producto producto, 
                       RedirectAttributes redirect) {
        try {
            service.crear(producto);
            redirect.addFlashAttribute("mensaje", "Producto creado exitosamente");
            redirect.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensaje", "Error: " + e.getMessage());
            redirect.addFlashAttribute("tipo", "error");
        }
        return "redirect:/productos";
    }

    /**
     * Muestra el formulario para editar un producto existente.
     * 
     * Busca el producto por ID y lo envía al formulario para que
     * el usuario pueda modificar sus datos.
     * 
     * @param id ID del producto a editar
     * @param model Modelo de Spring para pasar datos a la vista
     * @param redirect Atributos para redirigir con mensajes
     * @return El nombre de la plantilla HTML ("productos/formulario") o redirección si no existe
     */
    @GetMapping("/{id}/editar")
    public String editarForm(@PathVariable Integer id, Model model,
                            RedirectAttributes redirect) {
        Optional<Producto> p = service.obtenerPorId(id);
        if (p.isPresent()) {
            model.addAttribute("producto", p.get());
            return "productos/formulario";
        }
        redirect.addFlashAttribute("mensaje", "Producto no encontrado");
        redirect.addFlashAttribute("tipo", "error");
        return "redirect:/productos";
    }

    /**
     * Actualiza un producto existente en la base de datos.
     * 
     * Procesa el envío del formulario de edición, valida los datos
     * y actualiza el producto. Si hay error, muestra un mensaje de error.
     * 
     * @param id ID del producto a actualizar
     * @param producto Objeto Producto con los datos actualizados
     * @param redirect Atributos para redirigir con mensajes flash
     * @return Redirección a la lista de productos
     */
    @PostMapping("/{id}")
    public String actualizar(@PathVariable Integer id,
                            @ModelAttribute Producto producto,
                            RedirectAttributes redirect) {
        try {
            service.actualizar(id, producto);
            redirect.addFlashAttribute("mensaje", "Producto actualizado exitosamente");
            redirect.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensaje", "Error: " + e.getMessage());
            redirect.addFlashAttribute("tipo", "error");
        }
        return "redirect:/productos";
    }

    /**
     * Elimina un producto de la base de datos.
     * 
     * Recibe el ID del producto a eliminar y lo remueve de la BD.
     * Si hay error, muestra un mensaje de error.
     * 
     * @param id ID del producto a eliminar
     * @param redirect Atributos para redirigir con mensajes flash
     * @return Redirección a la lista de productos
     */
    @GetMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirect) {
        try {
            service.eliminar(id);
            redirect.addFlashAttribute("mensaje", "Producto eliminado exitosamente");
            redirect.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensaje", "Error: " + e.getMessage());
            redirect.addFlashAttribute("tipo", "error");
        }
        return "redirect:/productos";
    }

    /**
     * Redirige a la página de inicio (lista de productos).
     * 
     * @return Redirección a la lista de productos
     */
    @GetMapping("/")
    public String home() {
        return "redirect:/productos";
    }
}

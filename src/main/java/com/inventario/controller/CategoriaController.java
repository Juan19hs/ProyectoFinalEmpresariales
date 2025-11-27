package com.inventario.controller;

import com.inventario.model.Categoria;
import com.inventario.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

/**
 * Controlador para manejar CRUD de Categorías (solo administrador).
 *
 * Todas las rutas están bajo /admin/categorias y la protección se garantiza
 * mediante la configuración de seguridad (ROLE_ADMIN).
 */
@Controller
@RequestMapping("/admin/categorias")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    /**
     * Lista todas las categorías y las muestra en una vista sencilla.
     */
    @GetMapping
    public String listar(Model model) {
        List<Categoria> categorias = service.listarTodos();
        model.addAttribute("categorias", categorias);
        return "admin/categorias/listado";
    }

    /**
     * Muestra el formulario para crear una nueva categoría.
     */
    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "admin/categorias/formulario";
    }

    /**
     * Crea una nueva categoría.
     */
    @PostMapping
    public String crear(@Valid @ModelAttribute Categoria categoria, RedirectAttributes redirect) {
        try {
            service.crear(categoria);
            redirect.addFlashAttribute("mensaje", "Categoría creada correctamente");
            redirect.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensaje", "Error: " + e.getMessage());
            redirect.addFlashAttribute("tipo", "error");
        }
        return "redirect:/admin/categorias";
    }

    /**
     * Muestra el formulario de edición para una categoría existente.
     */
    @GetMapping("/{id}/editar")
    public String editarForm(@PathVariable Integer id, Model model, RedirectAttributes redirect) {
        Optional<Categoria> c = service.obtenerPorId(id);
        if (c.isPresent()) {
            model.addAttribute("categoria", c.get());
            return "admin/categorias/formulario";
        }
        redirect.addFlashAttribute("mensaje", "Categoría no encontrada");
        redirect.addFlashAttribute("tipo", "error");
        return "redirect:/admin/categorias";
    }

    /**
     * Actualiza una categoría existente.
     */
    @PostMapping("/{id}")
    public String actualizar(@PathVariable Integer id, @ModelAttribute Categoria categoria, RedirectAttributes redirect) {
        try {
            service.actualizar(id, categoria);
            redirect.addFlashAttribute("mensaje", "Categoría actualizada correctamente");
            redirect.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensaje", "Error: " + e.getMessage());
            redirect.addFlashAttribute("tipo", "error");
        }
        return "redirect:/admin/categorias";
    }

    /**
     * Elimina una categoría por su ID.
     */
    @GetMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirect) {
        try {
            service.eliminar(id);
            redirect.addFlashAttribute("mensaje", "Categoría eliminada correctamente");
            redirect.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirect.addFlashAttribute("mensaje", "Error: " + e.getMessage());
            redirect.addFlashAttribute("tipo", "error");
        }
        return "redirect:/admin/categorias";
    }
}

package com.inventario.controller;

import com.inventario.service.ProductoService;
import com.inventario.service.CategoriaService;
import com.inventario.model.Producto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

/**
 * Controlador para la sección de administración.
 *
 * Provee endpoints bajo /admin para ver estadísticas y opciones
 * de administración que sólo deben ser accesibles por administradores.
 *
 * Todas las rutas de este controlador están protegidas por la configuración
 * de seguridad y sólo pueden ser accesibles por usuarios con ROLE_ADMIN.
 *
 * @author Sistema de Inventario - Sexto Semestre
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param productoService Servicio para operaciones sobre productos
     */
    public AdminController(ProductoService productoService, CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    /**
     * Página principal del panel de administración.
     *
     * Muestra un resumen simple y enlaces a estadísticas y CRUD de categorías.
     *
     * @param model Modelo para enviar datos a la vista
     * @return Nombre de la plantilla para el panel de administración
     */
    @GetMapping
    public String panel(Model model) {
        // Añadir en el modelo un resumen simple (número de productos)
        List<Producto> todos = productoService.listarTodos();
        model.addAttribute("totalProductos", todos.size());
        model.addAttribute("totalCategorias", categoriaService.listarTodos().size());
        // Título para la plantilla base
        model.addAttribute("titulo", "Panel de Administración - Inventario");
        return "admin/panel";
    }

    /**
     * Página de estadísticas de productos.
     *
     * Calcula y muestra estadísticas sencillas: productos más costosos,
     * más baratos, mayor stock y menor stock.
     *
     * @param model Modelo para enviar datos a la vista
     * @return Nombre de la plantilla con las estadísticas
     */
    @GetMapping("/estadisticas")
    public String estadisticas(Model model) {
        model.addAttribute("masCostosos", productoService.obtenerMasCostosos(5));
        model.addAttribute("masBaratos", productoService.obtenerMasBaratos(5));
        model.addAttribute("mayorStock", productoService.obtenerMayorStock(5));
        model.addAttribute("menorStock", productoService.obtenerMenorStock(5));
        // Título para la plantilla base
        model.addAttribute("titulo", "Estadísticas de Productos - Admin");
        return "admin/estadisticas";
    }
}

package com.inventario.controller;

import com.inventario.model.Producto;
import com.inventario.service.ProductoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador simple para manejar un carrito de compras en sesión.
 *
 * Implementación minimalista para la fase académica: guarda en la sesión
 * un mapa productoId -> cantidad. No se persiste en BD ni se procesa pago.
 */
@Controller
@RequestMapping("/carrito")
public class CarritoController {

    private final ProductoService productoService;

    public CarritoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    /**
     * Añade un producto al carrito en la sesión. Si ya existe, incrementa la cantidad.
     *
     * @param id ID del producto a añadir
     * @param cantidad Cantidad a añadir (por defecto 1)
     * @param session HttpSession donde se guarda el carrito
     * @param redirect RedirectAttributes para mensajes flash
     * @return Redirección a la lista del carrito
     */
    @SuppressWarnings("unchecked")
    @GetMapping("/agregar/{id}")
    public String agregarAlCarrito(@PathVariable Integer id,
                                   @RequestParam(defaultValue = "1") Integer cantidad,
                                   HttpSession session,
                                   RedirectAttributes redirect) {
        Object raw = session.getAttribute("carrito");
        Map<Integer, Integer> carrito;
        if (raw instanceof Map) {
            carrito = (Map<Integer, Integer>) raw;
        } else {
            carrito = new HashMap<>();
        }
        carrito.put(id, carrito.getOrDefault(id, 0) + cantidad);
        session.setAttribute("carrito", carrito);
        redirect.addFlashAttribute("mensaje", "Producto añadido al carrito");
        redirect.addFlashAttribute("tipo", "success");
        return "redirect:/carrito";
    }

    /**
     * Elimina un producto del carrito (completamente).
     */
    @SuppressWarnings("unchecked")
    @GetMapping("/eliminar/{id}")
    public String eliminarDelCarrito(@PathVariable Integer id, HttpSession session, RedirectAttributes redirect) {
        Object raw = session.getAttribute("carrito");
        Map<Integer, Integer> carrito = raw instanceof Map ? (Map<Integer, Integer>) raw : new HashMap<>();
        if (carrito != null && carrito.containsKey(id)) {
            carrito.remove(id);
            session.setAttribute("carrito", carrito);
            redirect.addFlashAttribute("mensaje", "Producto eliminado del carrito");
            redirect.addFlashAttribute("tipo", "success");
        }
        return "redirect:/carrito";
    }

    /**
     * Lista los productos que están en el carrito y sus cantidades.
     */
    @SuppressWarnings("unchecked")
    @GetMapping
    public String listarCarrito(HttpSession session, Model model) {
        Object raw = session.getAttribute("carrito");
        Map<Integer, Integer> carrito = raw instanceof Map ? (Map<Integer, Integer>) raw : new HashMap<>();
        List<Producto> productos = new ArrayList<>();
        Map<Integer, Integer> cantidades = new HashMap<>();
        double total = 0.0;
        if (carrito != null) {
            for (Map.Entry<Integer, Integer> e : carrito.entrySet()) {
                Integer id = e.getKey();
                Integer cantidad = e.getValue();
                productoService.obtenerPorId(id).ifPresent(p -> {
                    productos.add(p);
                });
                cantidades.put(id, cantidad);
            }
            for (Producto p : productos) {
                total += p.getPrecio() * cantidades.get(p.getId());
            }
        }
        model.addAttribute("productosCarrito", productos);
        model.addAttribute("cantidades", cantidades);
        model.addAttribute("totalCarrito", total);
        return "carrito/listado";
    }
}

package com.eventos.controlador;

import com.eventos.modelo.entidad.Pago;
import com.eventos.modelo.servicio.ClienteServicio;
import com.eventos.modelo.servicio.EventoServicio;
import com.eventos.modelo.servicio.PagoServicio;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controlador para la gestión de Pagos y Finanzas.
 * Incluye lógica para calcular estadísticas de recaudación en tiempo real.
 */
@Controller
public class PagoControlador {

    @Autowired
    private PagoServicio servicio;

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private EventoServicio eventoServicio;

    /**
     * Muestra la vista de pagos con estadísticas calculadas al vuelo.
     */
    @GetMapping("/pagos")
    public String listar(HttpSession session, Model modelo) {

        if (session.getAttribute("usuarioLogueado") == null &&
            session.getAttribute("adminPrincipal") == null) {
            return "redirect:/";
        }

        List<Pago> pagos = servicio.listar();

        // --- CÁLCULO DE ESTADÍSTICAS ---
        
        // 1. Suma total de todos los montos
        double total = pagos.stream().mapToDouble(Pago::getMonto).sum();
        
        // 2. Identificar el método de pago más utilizado (PSE, Tarjeta, etc.)
        String metodoTop = "N/A";
        String metodoTopPct = "0%";
        if (!pagos.isEmpty()) {
            // Agrupamos por nombre de método y contamos las ocurrencias
            Map<String, Long> conteoMetodos = pagos.stream()
                    .collect(Collectors.groupingBy(Pago::getMetodoPago, Collectors.counting()));
            
            // Obtenemos el que tiene el valor (conteo) más alto
            metodoTop = conteoMetodos.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .get().getKey();
            
            // Calculamos el porcentaje que representa ese método sobre el total
            long count = conteoMetodos.get(metodoTop);
            metodoTopPct = String.format("%.1f%%", (count * 100.0) / pagos.size());
        }

        // Enviamos resultados formateados y listas de apoyo para los selectores
        modelo.addAttribute("pagos", pagos);
        modelo.addAttribute("totalRecaudado", String.format("$%,.0f", total));
        modelo.addAttribute("totalPagos", pagos.size());
        modelo.addAttribute("metodoTop", metodoTop);
        modelo.addAttribute("metodoTopPct", metodoTopPct);
        
        modelo.addAttribute("clientes", clienteServicio.listar());
        modelo.addAttribute("eventos", eventoServicio.listar());
        modelo.addAttribute("pago", new Pago());

        return "pagos";
    }

    /**
     * Guarda un registro de pago y redirige a la lista.
     */
    @PostMapping("/pagos/guardar")
    public String guardar(@ModelAttribute Pago pago, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null &&
            session.getAttribute("adminPrincipal") == null) {
            return "redirect:/";
        }
        servicio.guardar(pago);
        return "redirect:/pagos";
    }

    /**
     * Prepara la edición de un pago.
     */
    @GetMapping("/pagos/editar/{id}")
    public String editar(@PathVariable Long id, HttpSession session, Model modelo) {
        if (session.getAttribute("usuarioLogueado") == null &&
            session.getAttribute("adminPrincipal") == null) {
            return "redirect:/";
        }
        Pago pago = servicio.buscar(id).orElse(null);
        modelo.addAttribute("pago", pago);
        
        // Llamamos al método listar para que las estadísticas se vuelvan a cargar en el modo edición
        return listar(session, modelo);
    }

    /**
     * Elimina un registro de pago.
     */
    @GetMapping("/pagos/eliminar/{id}")
    public String eliminar(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null &&
            session.getAttribute("adminPrincipal") == null) {
            return "redirect:/";
        }
        servicio.eliminar(id);
        return "redirect:/pagos";
    }
}
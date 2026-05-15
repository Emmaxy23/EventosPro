package com.eventos.controlador;

import com.eventos.modelo.entidad.Reserva;
import com.eventos.modelo.servicio.ClienteServicio;
import com.eventos.modelo.servicio.EventoServicio;
import com.eventos.modelo.servicio.ReservaServicio;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para la gestión de Reservas.
 * Permite que los administradores agenden cupos para clientes en eventos específicos.
 */
@Controller
public class ReservaControlador {

    // Inyectamos los servicios necesarios para llenar los selectores (dropdowns) de la vista
    @Autowired
    private ReservaServicio servicio;

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private EventoServicio eventoServicio;

    /**
     * Muestra el listado de todas las reservas y el formulario de creación.
     */
    @GetMapping("/reservas")
    public String listarReservas(HttpSession session, Model modelo) {

        // Validación de sesión
        if (session.getAttribute("usuarioLogueado") == null &&
            session.getAttribute("adminPrincipal") == null) {
            return "redirect:/";
        }

        // Enviamos los datos necesarios a la vista Thymeleaf
        modelo.addAttribute("reservas", servicio.listar());
        modelo.addAttribute("clientes", clienteServicio.listar()); // Para el selector de clientes
        modelo.addAttribute("eventos", eventoServicio.listar());   // Para el selector de eventos
        modelo.addAttribute("reserva", new Reserva());           // Objeto vacío para el formulario
        
        return "reservas";
    }

    /**
     * Procesa el guardado de una reserva.
     */
    @PostMapping("/guardar-reserva")
    public String guardarReserva(@ModelAttribute Reserva reserva, HttpSession session) {

        if (session.getAttribute("usuarioLogueado") == null &&
            session.getAttribute("adminPrincipal") == null) {
            return "redirect:/";
        }

        servicio.guardar(reserva);
        return "redirect:/reservas";
    }

    /**
     * Carga una reserva específica en el formulario para editar sus datos.
     */
    @GetMapping("/editar-reserva/{id}")
    public String editarReserva(@PathVariable Long id, HttpSession session, Model modelo) {

        if (session.getAttribute("usuarioLogueado") == null &&
            session.getAttribute("adminPrincipal") == null) {
            return "redirect:/";
        }

        Reserva reserva = servicio.buscar(id).orElse(null);
        modelo.addAttribute("reserva", reserva);
        modelo.addAttribute("reservas", servicio.listar());
        modelo.addAttribute("clientes", clienteServicio.listar());
        modelo.addAttribute("eventos", eventoServicio.listar());
        
        return "reservas";
    }

    /**
     * Elimina una reserva de la base de datos.
     */
    @GetMapping("/eliminar-reserva/{id}")
    public String eliminarReserva(@PathVariable Long id, HttpSession session) {

        if (session.getAttribute("usuarioLogueado") == null &&
            session.getAttribute("adminPrincipal") == null) {
            return "redirect:/";
        }

        servicio.eliminar(id);
        return "redirect:/reservas";
    }
}
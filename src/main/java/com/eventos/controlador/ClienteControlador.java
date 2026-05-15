package com.eventos.controlador;

import com.eventos.modelo.entidad.Cliente;
import com.eventos.modelo.servicio.ClienteServicio;
import com.eventos.modelo.servicio.ReservaServicio;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para la gestión de Clientes.
 * Permite registrar, buscar, editar y ver el historial de cada cliente.
 */
@Controller
public class ClienteControlador {

    @Autowired
    private ClienteServicio servicio;

    @Autowired
    private ReservaServicio reservaServicio;

    /**
     * Lista todos los clientes o filtra según los parámetros de búsqueda.
     * @param nombre Filtro por nombre o apellido.
     * @param correo Filtro por dirección de correo.
     */
    @GetMapping("/clientes")
    public String listarClientes(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String correo,
            HttpSession session,
            Model modelo) {

        // Protección de ruta: Solo usuarios logueados
        if (session.getAttribute("usuarioLogueado") == null &&
            session.getAttribute("adminPrincipal") == null) {
            return "redirect:/";
        }

        List<Cliente> clientes;
        // Lógica de búsqueda: Si hay parámetros, se usa el buscador del repositorio
        if ((nombre != null && !nombre.isBlank()) ||
            (correo != null && !correo.isBlank())) {
            clientes = servicio.buscarPorCriterios(nombre, correo);
            modelo.addAttribute("busquedaActiva", true);
        } else {
            // Si no hay filtros, se listan todos
            clientes = servicio.listar();
            modelo.addAttribute("busquedaActiva", false);
        }

        // Se envían los datos y el estado de los filtros a la vista
        modelo.addAttribute("clientes", clientes);
        modelo.addAttribute("cliente", new Cliente()); // Objeto para el formulario de creación
        modelo.addAttribute("filtrNombre", nombre);
        modelo.addAttribute("filtrCorreo", correo);
        return "clientes";
    }

    /**
     * Muestra el perfil y el historial de reservas de un cliente específico.
     */
    @GetMapping("/clientes/historial/{id}")
    public String historial(@PathVariable Long id, HttpSession session, Model modelo) {

        if (session.getAttribute("usuarioLogueado") == null &&
            session.getAttribute("adminPrincipal") == null) {
            return "redirect:/";
        }

        Cliente cliente = servicio.buscar(id).orElse(null);
        if (cliente == null) return "redirect:/clientes";

        // Se cargan el cliente y sus reservas asociadas
        modelo.addAttribute("cliente", cliente);
        modelo.addAttribute("reservas", reservaServicio.listarPorCliente(id));
        return "cliente-historial";
    }

    /**
     * Guarda un cliente nuevo o actualiza uno existente.
     */
    @PostMapping("/guardar-cliente")
    public String guardarCliente(@ModelAttribute Cliente cliente, HttpSession session) {

        if (session.getAttribute("usuarioLogueado") == null &&
            session.getAttribute("adminPrincipal") == null) {
            return "redirect:/";
        }

        servicio.guardar(cliente);
        return "redirect:/clientes";
    }

    /**
     * Carga los datos de un cliente en el formulario para su edición.
     */
    @GetMapping("/editar-cliente/{id}")
    public String editarCliente(@PathVariable Long id, HttpSession session, Model modelo) {

        if (session.getAttribute("usuarioLogueado") == null &&
            session.getAttribute("adminPrincipal") == null) {
            return "redirect:/";
        }

        Cliente cliente = servicio.buscar(id).orElse(null);
        modelo.addAttribute("cliente", cliente);
        modelo.addAttribute("clientes", servicio.listar());
        return "clientes";
    }

    /**
     * Elimina un cliente permanentemente.
     */
    @GetMapping("/eliminar-cliente/{id}")
    public String eliminarCliente(@PathVariable Long id, HttpSession session) {

        if (session.getAttribute("usuarioLogueado") == null &&
            session.getAttribute("adminPrincipal") == null) {
            return "redirect:/";
        }

        servicio.eliminar(id);
        return "redirect:/clientes";
    }
}
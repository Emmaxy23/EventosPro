package com.eventos.controlador;

import com.eventos.modelo.entidad.Evento;
import com.eventos.modelo.servicio.EventoServicio;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EventoControlador {

    @Autowired
    private EventoServicio servicio;

    // =========================
    // LISTAR + BÚSQUEDA
    // =========================

    @GetMapping("/eventos")
    public String listar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String lugar,
            HttpSession session,
            Model modelo) {

        if (session.getAttribute("usuarioLogueado") == null &&
            session.getAttribute("adminPrincipal") == null) {
            return "redirect:/";
        }

        List<Evento> eventos;
        if ((nombre != null && !nombre.isBlank()) ||
            (tipo != null && !tipo.isBlank()) ||
            (lugar != null && !lugar.isBlank())) {
            eventos = servicio.buscarPorCriterios(nombre, tipo, lugar);
            modelo.addAttribute("busquedaActiva", true);
        } else {
            eventos = servicio.listar();
            modelo.addAttribute("busquedaActiva", false);
        }

        modelo.addAttribute("eventos", eventos);
        modelo.addAttribute("evento", new Evento());
        modelo.addAttribute("filtrNombre", nombre);
        modelo.addAttribute("filtrTipo", tipo);
        modelo.addAttribute("filtrLugar", lugar);
        return "eventos";
    }

    // =========================
    // DETALLE
    // =========================

    @GetMapping("/eventos/detalle/{id}")
    public String detalle(@PathVariable Long id, HttpSession session, Model modelo) {

        if (session.getAttribute("usuarioLogueado") == null &&
            session.getAttribute("adminPrincipal") == null) {
            return "redirect:/";
        }

        Evento evento = servicio.buscar(id).orElse(null);
        if (evento == null) return "redirect:/eventos";
        modelo.addAttribute("evento", evento);
        return "evento-detalle";
    }

    // =========================
    // GUARDAR
    // =========================

    @PostMapping("/guardar-evento")
    public String guardar(@ModelAttribute Evento evento, HttpSession session) {

        if (session.getAttribute("usuarioLogueado") == null &&
            session.getAttribute("adminPrincipal") == null) {
            return "redirect:/";
        }

        servicio.guardar(evento);
        return "redirect:/eventos";
    }

    // =========================
    // EDITAR
    // =========================

    @GetMapping("/editar-evento/{id}")
    public String editar(@PathVariable Long id, HttpSession session, Model modelo) {

        if (session.getAttribute("usuarioLogueado") == null &&
            session.getAttribute("adminPrincipal") == null) {
            return "redirect:/";
        }

        Evento evento = servicio.buscar(id).orElse(null);
        modelo.addAttribute("evento", evento);
        modelo.addAttribute("eventos", servicio.listar());
        return "eventos";
    }

    // =========================
    // ELIMINAR
    // =========================

    @GetMapping("/eliminar-evento/{id}")
    public String eliminar(@PathVariable Long id, HttpSession session) {

        if (session.getAttribute("usuarioLogueado") == null &&
            session.getAttribute("adminPrincipal") == null) {
            return "redirect:/";
        }

        servicio.eliminar(id);
        return "redirect:/eventos";
    }
}
package com.eventos.controlador;

import com.eventos.modelo.entidad.Admin;
import com.eventos.modelo.entidad.Pago;
import com.eventos.modelo.servicio.AdminServicio;
import com.eventos.modelo.servicio.ClienteServicio;
import com.eventos.modelo.servicio.EventoServicio;
import com.eventos.modelo.servicio.FacturaServicio;
import com.eventos.modelo.servicio.PagoServicio;
import com.eventos.modelo.servicio.ReservaServicio;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador principal para la gestión de administración y autenticación.
 * Maneja el acceso al sistema, el inicio de sesión y el dashboard principal.
 */
@Controller
public class AdminControlador {

    // Inyección de dependencias para acceder a la lógica de negocio de cada módulo
    @Autowired
    private AdminServicio servicio;

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private EventoServicio eventoServicio;

    @Autowired
    private ReservaServicio reservaServicio;

    @Autowired
    private PagoServicio pagoServicio;

    @Autowired
    private FacturaServicio facturaServicio;

    /**
     * Muestra la página de inicio (Login).
     */
    @GetMapping("/")
    public String mostrarLogin() {
        return "login";
    }

    /**
     * Procesa el intento de inicio de sesión.
     * @param username Nombre de usuario enviado desde el formulario.
     * @param password Contraseña enviada desde el formulario.
     * @param session Objeto para gestionar la sesión del usuario en el servidor.
     */
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        // Validación para el Super Admin (Credenciales fijas para mantenimiento)
        if (username.equals("Admins") && password.equals("12345")) {
            session.setAttribute("adminPrincipal", true); // Marca la sesión como administrador principal
            return "redirect:/admins";
        }

        // Validación para administradores registrados en la base de datos
        Admin admin = servicio.login(username, password);

        if (admin != null) {
            session.setAttribute("usuarioLogueado", admin); // Guarda el objeto admin en la sesión
            return "redirect:/index";
        } else {
            model.addAttribute("error", "Credenciales incorrectas"); // Envía mensaje de error a la vista
            return "login";
        }
    }

    /**
     * Cierra la sesión actual y redirige al login.
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Destruye todos los datos de la sesión actual
        return "redirect:/";
    }

    /**
     * Muestra el formulario de registro para nuevos administradores.
     */
    @GetMapping("/registro")
    public String mostrarRegistro(Model modelo) {
        modelo.addAttribute("admin", new Admin()); // Envía un objeto vacío para el formulario
        return "registro";
    }

    /**
     * Guarda un nuevo administrador en la base de datos.
     */
    @PostMapping("/registrar")
    public String registrar(@ModelAttribute Admin admin) {
        servicio.guardar(admin);
        return "redirect:/";
    }

    /**
     * Página principal del sistema (Dashboard).
     * Muestra estadísticas generales recolectadas de todos los servicios.
     */
    @GetMapping("/index")
    public String index(HttpSession session, Model model) {

        // Verificación de seguridad: si no hay sesión, redirige al login
        if (session.getAttribute("usuarioLogueado") == null && session.getAttribute("adminPrincipal") == null) {
            return "redirect:/";
        }

        // Recolección de estadísticas reales para mostrar en las tarjetas del Dashboard
        model.addAttribute("totalClientes", clienteServicio.listar().size());
        model.addAttribute("totalEventos", eventoServicio.listar().size());
        model.addAttribute("totalReservas", reservaServicio.listar().size());
        
        // Sumatoria de todos los montos de pago registrados
        double totalPagos = pagoServicio.listar().stream().mapToDouble(Pago::getMonto).sum();
        model.addAttribute("totalPagosFormateado", formatMonto(totalPagos));
        
        model.addAttribute("totalFacturas", facturaServicio.listar().size());
        model.addAttribute("totalAdmins", servicio.listar().size());

        return "index";
    }

    /**
     * Utilidad para formatear montos grandes (ej: 1.5M o 10.2k).
     */
    private String formatMonto(double monto) {
        if (monto >= 1000000) {
            return String.format(java.util.Locale.US, "$%.1fM", monto / 1000000.0);
        } else if (monto >= 1000) {
            return String.format(java.util.Locale.US, "$%.1fk", monto / 1000.0);
        } else {
            return String.format(java.util.Locale.US, "$%.2f", monto);
        }
    }

    /**
     * Lista todos los administradores (Solo accesible para el Super Admin).
     */
    @GetMapping("/admins")
    public String listarAdmins(HttpSession session, Model modelo) {
        if (session.getAttribute("adminPrincipal") == null) {
            return "redirect:/";
        }
        modelo.addAttribute("admins", servicio.listar());
        modelo.addAttribute("admin", new Admin());
        return "admins";
    }

    /**
     * Guarda o actualiza un administrador.
     */
    @PostMapping("/guardar-admin")
    public String guardarAdmin(@ModelAttribute Admin admin, HttpSession session) {
        if (session.getAttribute("adminPrincipal") == null) {
            return "redirect:/";
        }
        servicio.guardar(admin);
        return "redirect:/admins";
    }

    /**
     * Prepara el formulario para editar un administrador existente.
     */
    @GetMapping("/editar-admin/{id}")
    public String editarAdmin(@PathVariable Long id, HttpSession session, Model modelo) {
        if (session.getAttribute("adminPrincipal") == null) {
            return "redirect:/";
        }
        Admin admin = servicio.buscar(id).orElse(null);
        modelo.addAttribute("admins", servicio.listar());
        modelo.addAttribute("admin", admin);
        return "admins";
    }

    /**
     * Elimina un administrador por su ID.
     */
    @GetMapping("/eliminar-admin/{id}")
    public String eliminarAdmin(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("adminPrincipal") == null) {
            return "redirect:/";
        }
        servicio.eliminar(id);
        return "redirect:/admins";
    }
}
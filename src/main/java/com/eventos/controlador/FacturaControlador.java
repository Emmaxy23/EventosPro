package com.eventos.controlador;

import com.eventos.modelo.entidad.Factura;
import com.eventos.modelo.entidad.FacturaDetalle;
import com.eventos.modelo.servicio.FacturaServicio;
import com.eventos.utilidades.PdfGenerador;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/facturas")
public class FacturaControlador {

    @Autowired
    private FacturaServicio servicio;

    @GetMapping
    public String listar(Model modelo) {
        modelo.addAttribute("facturas", servicio.listar());
        modelo.addAttribute("factura", new Factura());
        return "facturas";
    }

    @PostMapping("/guardar")
    public String guardar(
            @ModelAttribute Factura factura,
            @RequestParam List<String> producto,
            @RequestParam List<Integer> cantidad,
            @RequestParam List<Double> precio) {

        List<FacturaDetalle> items = new ArrayList<>();

        for (int i = 0; i < producto.size(); i++) {

            if (producto.get(i) == null || producto.get(i).isEmpty()) {
                continue;
            }

            FacturaDetalle item = new FacturaDetalle();
            item.setProducto(producto.get(i));
            item.setCantidad(cantidad.get(i));
            item.setPrecio(precio.get(i));

            items.add(item);
        }

        factura.setItems(items);
        servicio.guardar(factura);

        return "redirect:/facturas";
    }

    @GetMapping("/pdf/{id}")
    public void generarPdf(@PathVariable Long id, HttpServletResponse response) throws Exception {

        Factura factura = servicio.buscar(id).orElse(null);

        if (factura == null) {
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "attachment; filename=factura_" + factura.getIdFactura() + ".pdf");

        PdfGenerador.generarFacturaPdf(factura, response.getOutputStream());
    }

    @GetMapping("/nueva")
    public String nuevaFactura(Model modelo) {
        modelo.addAttribute("factura", new Factura());
        return "nueva-factura";
    }
    @GetMapping("/eliminar/{id}")
public String eliminar(@PathVariable Long id) {
    servicio.eliminar(id);
    return "redirect:/facturas";
}
}

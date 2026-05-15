package com.eventos.modelo.servicio;

import com.eventos.modelo.entidad.Factura;
import com.eventos.modelo.entidad.FacturaDetalle;
import com.eventos.modelo.repositorio.FacturaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FacturaServicio {

    private static final double IVA_POR = 0.19;

    @Autowired
    private FacturaRepositorio repo;

    public Factura guardar(Factura factura) {

        double subtotal = 0;

        for (FacturaDetalle item : factura.getItems()) {
            double sub = item.getPrecio() * item.getCantidad();
            item.setSubtotal(sub);
            item.setFactura(factura);
            subtotal += sub;
        }

        factura.setSubtotal(subtotal);
        factura.setIva(subtotal * IVA_POR);
        factura.setTotal(subtotal + factura.getIva());

        if (factura.getFecha() == null) {
            factura.setFecha(LocalDate.now());
        }

        return repo.save(factura);
    }

    public List<Factura> listar() {
        return repo.findAll();
    }

    public Optional<Factura> buscar(Long id) {
        return repo.findById(id);
    }
    public void eliminar(Long id) {
    repo.deleteById(id);
}
}
package com.eventos.modelo.repositorio;

import com.eventos.modelo.entidad.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepositorio extends JpaRepository<Factura, Long> {
}
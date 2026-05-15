package com.eventos.modelo.repositorio;

import com.eventos.modelo.entidad.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepositorio extends JpaRepository<Pago, Long> {
}
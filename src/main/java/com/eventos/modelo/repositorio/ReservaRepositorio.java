package com.eventos.modelo.repositorio;

import com.eventos.modelo.entidad.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepositorio extends JpaRepository<Reserva, Long> {
    List<Reserva> findByCliente_IdCliente(Long idCliente);
}
package com.eventos.modelo.servicio;

import com.eventos.modelo.entidad.Reserva;
import com.eventos.modelo.repositorio.ReservaRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaServicio {

    @Autowired
    private ReservaRepositorio repo;

    public List<Reserva> listar(){
        return repo.findAll();
    }

    public List<Reserva> listarPorCliente(Long idCliente) {
        return repo.findByCliente_IdCliente(idCliente);
    }

    public void guardar(Reserva reserva){
        repo.save(reserva);
    }

    public Optional<Reserva> buscar(Long id){
        return repo.findById(id);
    }

    public void eliminar(Long id){
        repo.deleteById(id);
    }
}
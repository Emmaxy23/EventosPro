package com.eventos.modelo.servicio;

import com.eventos.modelo.entidad.Pago;
import com.eventos.modelo.repositorio.PagoRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagoServicio {

    @Autowired
    private PagoRepositorio repo;

    // LISTAR
    public List<Pago> listar(){

        return repo.findAll();
    }

    // GUARDAR
    public void guardar(Pago pago){

        repo.save(pago);
    }

    // BUSCAR
    public Optional<Pago> buscar(Long id){

        return repo.findById(id);
    }

    // ELIMINAR
    public void eliminar(Long id){

        repo.deleteById(id);
    }
}
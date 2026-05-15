package com.eventos.modelo.servicio;

import com.eventos.modelo.entidad.Evento;
import com.eventos.modelo.repositorio.EventoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio encargado de gestionar la lógica de negocio de los Eventos.
 * Actúa como puente entre el Controlador y el Repositorio de base de datos.
 */
@Service
public class EventoServicio {

    // Repositorio JPA para realizar operaciones CRUD
    @Autowired
    private EventoRepositorio repo;

    /**
     * Guarda un evento nuevo o actualiza uno existente.
     */
    public Evento guardar(Evento evento) {
        return repo.save(evento);
    }

    /**
     * Obtiene todos los eventos registrados.
     */
    public List<Evento> listar() {
        return repo.findAll();
    }

    /**
     * Busca un evento específico por su ID único.
     */
    public Optional<Evento> buscar(Long id) {
        return repo.findById(id);
    }

    /**
     * Realiza una búsqueda filtrada por criterios múltiples.
     */
    public List<Evento> buscarPorCriterios(String nombre, String tipo, String lugar) {
        return repo.buscarPorCriterios(nombre, tipo, lugar);
    }

    /**
     * Actualiza los datos de un evento existente.
     * @param id ID del evento a modificar.
     * @param nuevo Objeto con los nuevos datos enviados desde el formulario.
     */
    public Evento actualizar(Long id, Evento nuevo) {
        return repo.findById(id).map(evento -> {
            // Mapeo de campos para asegurar que se conserven los datos correctos
            evento.setNombre(nuevo.getNombre());
            evento.setTipo(nuevo.getTipo());
            evento.setFecha(nuevo.getFecha());
            evento.setLugar(nuevo.getLugar());
            evento.setDescripcion(nuevo.getDescripcion());
            evento.setPrecio(nuevo.getPrecio());
            evento.setCupoDisponible(nuevo.getCupoDisponible());
            evento.setArtistas(nuevo.getArtistas());
            evento.setHora(nuevo.getHora());
            evento.setImagenUrl(nuevo.getImagenUrl());
            evento.setPrograma(nuevo.getPrograma());
            return repo.save(evento); // Guarda los cambios en la base de datos
        }).orElse(null);
    }

    /**
     * Elimina un evento si existe en la base de datos.
     */
    public boolean eliminar(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}
package com.eventos.modelo.repositorio;

import com.eventos.modelo.entidad.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interfaz de Repositorio para la entidad Evento.
 * JpaRepository ya incluye métodos como findAll(), findById(), save() y deleteById().
 */
@Repository
public interface EventoRepositorio extends JpaRepository<Evento, Long> {

    /**
     * Consulta personalizada utilizando JPQL (Java Persistence Query Language).
     * Permite buscar eventos que coincidan parcialmente con nombre, tipo o lugar.
     * 
     * 'LOWER' se usa para que la búsqueda no dependa de mayúsculas o minúsculas.
     * 'LIKE %:param%' busca cualquier texto que contenga la palabra ingresada.
     */
    @Query("SELECT e FROM Evento e WHERE " +
           "(:nombre IS NULL OR LOWER(e.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) AND " +
           "(:tipo IS NULL OR LOWER(e.tipo) LIKE LOWER(CONCAT('%', :tipo, '%'))) AND " +
           "(:lugar IS NULL OR LOWER(e.lugar) LIKE LOWER(CONCAT('%', :lugar, '%')))")
    List<Evento> buscarPorCriterios(
        @Param("nombre") String nombre, 
        @Param("tipo") String tipo, 
        @Param("lugar") String lugar
    );
}
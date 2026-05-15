package com.eventos.modelo.repositorio;

import com.eventos.modelo.entidad.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para Clientes.
 */
@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {

    /**
     * Búsqueda personalizada por nombre, apellido o correo.
     * Utiliza concatenación de strings para buscar coincidencias parciales.
     */
    @Query("SELECT c FROM Cliente c WHERE " +
           "(:nombre IS NULL OR LOWER(CONCAT(c.nombre, ' ', c.apellido)) LIKE LOWER(CONCAT('%', :nombre, '%'))) AND " +
           "(:correo IS NULL OR LOWER(c.correo) LIKE LOWER(CONCAT('%', :correo, '%')))")
    List<Cliente> buscarPorCriterios(@Param("nombre") String nombre, @Param("correo") String correo);
}
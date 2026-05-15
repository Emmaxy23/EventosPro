package com.eventos.modelo.servicio;

import com.eventos.modelo.entidad.Cliente;
import com.eventos.modelo.repositorio.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de Clientes.
 * Gestiona la validación y el flujo de datos para los registros de personas.
 */
@Service
public class ClienteServicio {

    @Autowired
    private ClienteRepositorio repo;

    public List<Cliente> listar() {
        return repo.findAll();
    }

    /**
     * Busca clientes usando filtros dinámicos.
     */
    public List<Cliente> buscarPorCriterios(String nombre, String correo) {
        return repo.buscarPorCriterios(nombre, correo);
    }

    public Cliente guardar(Cliente cliente) {
        return repo.save(cliente);
    }

    public Optional<Cliente> buscar(Long id) {
        return repo.findById(id);
    }

    /**
     * Actualiza los datos de un cliente existente de forma segura.
     */
    public Cliente actualizar(Long id, Cliente nuevo) {
        return repo.findById(id).map(cliente -> {
            cliente.setNombre(nuevo.getNombre());
            cliente.setApellido(nuevo.getApellido());
            cliente.setEdad(nuevo.getEdad());
            cliente.setCorreo(nuevo.getCorreo());
            cliente.setTelefono(nuevo.getTelefono());
            cliente.setPreferencias(nuevo.getPreferencias());
            return repo.save(cliente);
        }).orElse(null);
    }

    public boolean eliminar(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}
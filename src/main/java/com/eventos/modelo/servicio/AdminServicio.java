package com.eventos.modelo.servicio;

import com.eventos.modelo.entidad.Admin;
import com.eventos.modelo.repositorio.AdminRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServicio {

    @Autowired
    private AdminRepositorio repo;

    // LOGIN
    public Admin login(String username, String password) {

        Admin admin = repo.findByUsername(username);

        if (admin != null &&
            admin.getPassword().equals(password)) {

            return admin;
        }

        return null;
    }

    // LISTAR
    public List<Admin> listar() {

        return repo.findAll();
    }

    // GUARDAR
    public void guardar(Admin admin) {

        repo.save(admin);
    }

    // BUSCAR
    public Optional<Admin> buscar(Long id) {

        return repo.findById(id);
    }

    // ELIMINAR
    public void eliminar(Long id) {

        repo.deleteById(id);
    }
}
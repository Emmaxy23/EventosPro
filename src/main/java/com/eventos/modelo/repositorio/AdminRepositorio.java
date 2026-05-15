package com.eventos.modelo.repositorio;

import com.eventos.modelo.entidad.Admin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepositorio extends JpaRepository<Admin, Long> {

    Admin findByUsername(String username);

}
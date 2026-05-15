package com.eventos.modelo.entidad;

import jakarta.persistence.*;

@Entity
@Table(name = "admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_admin")
    private Long idAdmin;

    private String username;

    private String password;

    public Admin() {
    }

    public Admin(Long idAdmin, String username, String password) {
        this.idAdmin = idAdmin;
        this.username = username;
        this.password = password;
    }

    public Long getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Long idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
package com.eventos.modelo.entidad;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    private String nombre;
    private String apellido;
    private int edad;
    private String correo;
    private String telefono;
    private String preferencias;

    // 🔥 RELACIÓN CON RESERVAS
    @OneToMany(
        mappedBy = "cliente",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Reserva> reservas;

    // 🔥 RELACIÓN CON PAGOS
    @OneToMany(
        mappedBy = "cliente",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Pago> pagos;

    public Cliente() {
    }

    public Cliente(Long idCliente, String nombre, String apellido,
                   int edad, String correo, String telefono) {

        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.correo = correo;
        this.telefono = telefono;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(String preferencias) {
        this.preferencias = preferencias;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }

    @Override
    public String toString() {
        return "Cliente {\n"
                + " idCliente = " + idCliente + "\n"
                + " nombre = " + nombre + "\n"
                + " apellido = " + apellido + "\n"
                + " edad = " + edad + "\n"
                + " correo = " + correo + "\n"
                + " telefono = " + telefono + "\n"
                + "}";
    }
}
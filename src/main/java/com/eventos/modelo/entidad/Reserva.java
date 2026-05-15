package com.eventos.modelo.entidad;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReserva;

    private int cantidadEntradas;
    private String estado;

    private LocalDate fechaReserva; // 🔥 NUEVO CAMPO

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_evento")
    private Evento evento;

    // 🔥 CONSTRUCTOR IMPORTANTE
    public Reserva() {
        this.cliente = new Cliente();
        this.evento = new Evento();
    }

    // GETTERS Y SETTERS
    public Long getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Long idReserva) {
        this.idReserva = idReserva;
    }

    public int getCantidadEntradas() {
        return cantidadEntradas;
    }

    public void setCantidadEntradas(int cantidadEntradas) {
        this.cantidadEntradas = cantidadEntradas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }
}

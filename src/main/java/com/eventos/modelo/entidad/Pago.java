package com.eventos.modelo.entidad;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Representa un Pago realizado por un cliente para un evento.
 */
@Entity
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;

    private double monto; // Cantidad de dinero pagada
    private String metodoPago; // PSE, Débito, Crédito, Efectivo
    private LocalDate fechaPago; // Fecha en la que se registró el dinero
    private int cantidadEntradas; // Número de boletos cubiertos por este pago

    /**
     * Relación: Muchos pagos pueden pertenecer a un mismo Cliente.
     */
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    /**
     * Relación: Muchos pagos pueden estar dirigidos a un mismo Evento.
     */
    @ManyToOne
    @JoinColumn(name = "id_evento")
    private Evento evento;

    /**
     * Constructor por defecto. 
     * Se inicializan los objetos internos para evitar errores de puntero nulo (NullPointerException) 
     * al renderizar el formulario en Thymeleaf.
     */
    public Pago() {
        this.cliente = new Cliente();
        this.evento = new Evento();
    }

    // --- GETTERS Y SETTERS ---
    // (Permiten que Spring y Hibernate lean y escriban los valores de los atributos privados)

    public Long getIdPago() {
        return idPago;
    }

    public void setIdPago(Long idPago) {
        this.idPago = idPago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public int getCantidadEntradas() {
        return cantidadEntradas;
    }

    public void setCantidadEntradas(int cantidadEntradas) {
        this.cantidadEntradas = cantidadEntradas;
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
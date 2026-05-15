package com.eventos.modelo.entidad;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "eventos")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvento;

    private String nombre;
    private String tipo;
    private LocalDate fecha;
    private String lugar;
    private String descripcion;
    private double precio;
    private int cupoDisponible;
    private String artistas;
    private LocalTime hora;
    private String imagenUrl;
    @Column(columnDefinition = "TEXT")
    private String programa;

    // 🔥 RELACIÓN CON RESERVAS
    @OneToMany(
        mappedBy = "evento",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Reserva> reservas;

    // 🔥 RELACIÓN CON PAGOS
    @OneToMany(
        mappedBy = "evento",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Pago> pagos;

    public Evento() {
    }

    public Evento(Long idEvento, String nombre, String tipo,
                  LocalDate fecha, String lugar,
                  String descripcion, double precio,
                  int cupoDisponible) {

        this.idEvento = idEvento;
        this.nombre = nombre;
        this.tipo = tipo;
        this.fecha = fecha;
        this.lugar = lugar;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cupoDisponible = cupoDisponible;
    }

    public Long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCupoDisponible() {
        return cupoDisponible;
    }

    public void setCupoDisponible(int cupoDisponible) {
        this.cupoDisponible = cupoDisponible;
    }

    public String getArtistas() {
        return artistas;
    }

    public void setArtistas(String artistas) {
        this.artistas = artistas;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
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
        return "Evento {\n" +
                "  idEvento = " + idEvento + "\n" +
                "  nombre = " + nombre + "\n" +
                "  tipo = " + tipo + "\n" +
                "  fecha = " + fecha + "\n" +
                "  lugar = " + lugar + "\n" +
                "  descripcion = " + descripcion + "\n" +
                "  precio = " + precio + "\n" +
                "  cupoDisponible = " + cupoDisponible + "\n" +
                "}";
    }
}
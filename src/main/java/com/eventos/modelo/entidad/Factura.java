package com.eventos.modelo.entidad;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa una Factura emitida por la empresa.
 * Contiene información general del cliente y una lista de ítems detallados.
 */
@Entity
@Table(name = "facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFactura;

    private String codigo; // Identificador comercial (ej: FAC-001)
    private String cliente; // Nombre del cliente (almacenado como texto en la factura)
    private LocalDate fecha; // Fecha de emisión
    private Double subtotal; // Suma de los precios * cantidades antes de impuestos
    private Double iva; // Valor del impuesto (generalmente 19%)
    private Double total; // Valor final a pagar

    /**
     * Relación Uno a Muchos con FacturaDetalle.
     * cascade = CascadeType.ALL: Si se guarda la factura, se guardan automáticamente sus detalles.
     * fetch = FetchType.EAGER: Carga los detalles inmediatamente al buscar la factura.
     */
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<FacturaDetalle> items = new ArrayList<>();

    public Factura() {
    }

    // --- GETTERS Y SETTERS ---

    public Long getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Long idFactura) {
        this.idFactura = idFactura;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<FacturaDetalle> getItems() {
        return items;
    }

    public void setItems(List<FacturaDetalle> items) {
        this.items = items;
    }
}

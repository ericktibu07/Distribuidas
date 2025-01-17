package com.pedido.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_pedido", nullable = false, unique = true)
    @NotBlank(message = "El número de pedido no puede estar vacío")
    @Pattern(regexp = "^PED-[0-9]{3}$",
            message = "El número de pedido debe tener el formato PED-XXX donde X son números")
    private String numeroPedido;

    @Column(nullable = false)
    @NotBlank(message = "El nombre del cliente no puede estar vacío")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{2,50}$",
            message = "El nombre del cliente debe tener entre 2 y 50 caracteres y solo puede contener letras y espacios")
    private String cliente;

    @Column(name = "fecha_pedido", nullable = false)
    @NotNull(message = "La fecha de pedido no puede ser nula")
    private LocalDate fechaPedido;

    @Column(nullable = false)
    @NotNull(message = "El total no puede ser nulo")
    @Min(value = 1, message = "El total debe ser mayor a 0")
    @Max(value = 999999999, message = "El total no puede exceder 999,999,999")
    private Integer total;

    // Constructor por defecto
    public Pedido() {
        this.fechaPedido = LocalDate.now();
    }

    // Constructor con parámetros
    public Pedido(String numeroPedido, String cliente, Integer total) {
        this();
        this.numeroPedido = numeroPedido;
        this.cliente = cliente;
        this.total = total;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public LocalDate getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDate fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    // Método toString para debugging
    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", numeroPedido='" + numeroPedido + '\'' +
                ", cliente='" + cliente + '\'' +
                ", fechaPedido=" + fechaPedido +
                ", total=" + total +
                '}';
    }
}
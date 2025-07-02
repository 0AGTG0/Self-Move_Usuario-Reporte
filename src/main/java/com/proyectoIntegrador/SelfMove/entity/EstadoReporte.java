package com.proyectoIntegrador.SelfMove.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "estado_reporte")
public class EstadoReporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado")
    private Integer idEstado;

    @Column(name = "nombre_estado", nullable = false, unique = true, length = 50)
    private String nombreEstado;

    public EstadoReporte() {
    }

    public EstadoReporte(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }
}
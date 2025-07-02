package com.proyectoIntegrador.SelfMove.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reporte")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "folio")
    private Integer folio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", nullable = true)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo", referencedColumnName = "id_tipo", nullable = false)
    private TipoReporte tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado", referencedColumnName = "id_estado", nullable = false)
    private EstadoReporte estado;

    @Column(name = "fecha_hora_reporte", columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime fechaHoraReporte;

    @Column(name = "contenido", columnDefinition = "TEXT", nullable = false)
    private String contenido;

    // Constructores, getters y setters

    public Reporte() {
    }
    public Reporte(Usuario usuario, TipoReporte tipo, EstadoReporte estado, String contenido, LocalDateTime fechaHoraReporte) {
        this.usuario = usuario;
        this.tipo = tipo;
        this.estado = estado;
        this.contenido = contenido;
        this.fechaHoraReporte = fechaHoraReporte;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getFolio() {
        return folio;
    }
    public void setFolio(Integer folio) {
        this.folio = folio;
    }

    public TipoReporte getTipo() {
        return tipo;
    }
    public void setTipo(TipoReporte tipo) {
        this.tipo = tipo;
    }

    public EstadoReporte getEstado() {
        return estado;
    }
    public void setEstado(EstadoReporte estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaHoraReporte() {
        return fechaHoraReporte;
    }
    public void setFechaHoraReporte(LocalDateTime fechaHoraReporte) {
        this.fechaHoraReporte = fechaHoraReporte;
    }

    public String getContenido() {
        return contenido;
    }
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
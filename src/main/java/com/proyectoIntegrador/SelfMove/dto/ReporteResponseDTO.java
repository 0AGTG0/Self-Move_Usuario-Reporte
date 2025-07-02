package com.proyectoIntegrador.SelfMove.dto;

import java.time.LocalDateTime;

/**
 * Clase DTO (Data Transfer Object) para representar los datos de un reporte
 * cuando se envían como respuesta al cliente (GET requests).
 * Incluye los nombres de las entidades relacionadas para una mejor visualización.
 */
public class ReporteResponseDTO {

    private Integer folio;
    private Integer idUsuario; // ID del usuario, puede ser null
    private String nombreUsuario; // Nombre del usuario, null si anónimo
    private Integer idTipo;
    private String nombreTipo; // Nombre del tipo de reporte
    private Integer idEstado;
    private String nombreEstado; // Nombre del estado del reporte
    private String contenido;
    private LocalDateTime fechaHoraReporte;

    // Constructor vacío
    public ReporteResponseDTO() {}

    // Constructor completo
    public ReporteResponseDTO(Integer folio, Integer idUsuario, String nombreUsuario, Integer idTipo, String nombreTipo, Integer idEstado, String nombreEstado, String contenido, LocalDateTime fechaHoraReporte) {
        this.folio = folio;
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.idTipo = idTipo;
        this.nombreTipo = nombreTipo;
        this.idEstado = idEstado;
        this.nombreEstado = nombreEstado;
        this.contenido = contenido;
        this.fechaHoraReporte = fechaHoraReporte;
    }

    // Getters y Setters
    public Integer getFolio() {
        return folio;
    }

    public void setFolio(Integer folio) {
        this.folio = folio;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
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

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFechaHoraReporte() {
        return fechaHoraReporte;
    }

    public void setFechaHoraReporte(LocalDateTime fechaHoraReporte) {
        this.fechaHoraReporte = fechaHoraReporte;
    }
}
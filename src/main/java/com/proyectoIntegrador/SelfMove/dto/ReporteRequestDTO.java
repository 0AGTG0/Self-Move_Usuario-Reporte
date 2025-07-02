package com.proyectoIntegrador.SelfMove.dto;

import java.time.LocalDateTime;

/**
 * Clase DTO (Data Transfer Object) para representar los datos mínimos
 * que se necesitan para crear o actualizar un reporte desde el cliente.
 *
 * Su objetivo es evitar que el cliente tenga que conocer o enviar el
 * objeto Reporte completo con todas las relaciones anidadas.
 * Recibe los IDs de las entidades relacionadas.
 */
public class ReporteRequestDTO {

    // El ID del usuario que reporta (puede ser null si es anónimo)
    private Integer idUsuario;

    // El ID del tipo de reporte (por ejemplo: queja, sugerencia, etc.)
    private Integer idTipo;

    // El ID del estado inicial del reporte (por ejemplo: pendiente, en revisión, etc.)
    private Integer idEstado;

    // El contenido del reporte que el usuario desea comunicar
    private String contenido;

    // Fecha y hora en que se realiza el reporte (opcional, si el cliente la provee)
    private LocalDateTime fechaHoraReporte;

    // -------------------------------
    // Constructores
    // -------------------------------

    // Constructor vacío (obligatorio para que Spring pueda hacer la conversión JSON -> Objeto)
    public ReporteRequestDTO() {}

    // Constructor completo útil para inicializar desde pruebas o código manual
    public ReporteRequestDTO(Integer idUsuario, Integer idTipo, Integer idEstado, String contenido, LocalDateTime fechaHoraReporte) {
        this.idUsuario = idUsuario;
        this.idTipo = idTipo;
        this.idEstado = idEstado;
        this.contenido = contenido;
        this.fechaHoraReporte = fechaHoraReporte;
    }

    // -------------------------------
    // Getters y Setters (métodos de acceso)
    // -------------------------------

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
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
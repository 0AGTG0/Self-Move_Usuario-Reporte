package com.proyectoIntegrador.SelfMove.dto;

import java.time.LocalDateTime;

/**
 * Clase DTO (Data Transfer Object) para representar los datos de un Usuario
 * cuando se envían como respuesta al cliente (GET requests).
 * Excluye información sensible como la contraseña hasheada.
 */
public class UsuarioResponseDTO {

    private Integer idUsuario;
    private String nombre;
    private String direccion;
    private String correo;
    private Boolean activo;
    private LocalDateTime fechaHoraRegistro;

    // Constructor vacío (necesario para la deserialización de Spring/Jackson)
    public UsuarioResponseDTO() {
    }

    // Constructor completo para facilitar el mapeo desde la entidad Usuario
    public UsuarioResponseDTO(Integer idUsuario, String nombre, String direccion, String correo, Boolean activo, LocalDateTime fechaHoraRegistro) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.direccion = direccion;
        this.correo = correo;
        this.activo = activo;
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    // --- Getters y Setters ---

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    public void setFechaHoraRegistro(LocalDateTime fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }
}
package com.proyectoIntegrador.SelfMove.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuario")
public class Usuario {

    /*
    * @Id indica que es el identificador unico
    * @GeneratedValue(strategy = GenerationType.IDENTITY) es para decirle que el id es auto incremental
    * en la base de datos
    * @Column dice que se refiere a un dato de la tabla en la base de datos
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String direccion;

    @Column(nullable = false, unique = true, length = 100)
    private String correo;

    @Column(name = "contrasenia_hash", nullable = false, length = 255)
    private String contraseniaHash;

    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean activo;

    @Column(name = "fecha_hora_registro", columnDefinition = "DATETIME")
    private LocalDateTime fechaHoraRegistro;

    //Constructores
    public Usuario() {
    }
    public Usuario(String nombre, String direccion, String correo, String contraseniaHash, Boolean activo, LocalDateTime fechaHoraRegistro) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.correo = correo;
        this.contraseniaHash = contraseniaHash;
        this.activo = activo;
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    // Getters y setters de cada tipo de dato

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

    public String getContraseniaHash() {
        return contraseniaHash;
    }
    public void setContraseniaHash(String contraseniaHash) {
        this.contraseniaHash = contraseniaHash;
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
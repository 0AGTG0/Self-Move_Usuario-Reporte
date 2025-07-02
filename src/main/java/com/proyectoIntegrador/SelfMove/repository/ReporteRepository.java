package com.proyectoIntegrador.SelfMove.repository;

import com.proyectoIntegrador.SelfMove.entity.Reporte;
import com.proyectoIntegrador.SelfMove.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Integer> {

    // Buscar todos los reportes creados por un usuario
    List<Reporte> findByUsuario(Usuario usuario);

    // Buscar por un rango de fecha
    List<Reporte> findByFechaHoraReporteBetween(LocalDateTime desde, LocalDateTime hasta);

    // Buscar todos los reportes que contienen una palabra clave en el contenido
    List<Reporte> findByContenidoContainingIgnoreCase(String palabraClave);

    // buscar los reportes con id de usuario null
    List<Reporte> findByUsuarioIsNull();

    // buscar reportes por el id del usuario
    List<Reporte> findByUsuario_IdUsuario(Integer idUsuario);

    // buscar por el id de tipo
    List<Reporte> findByTipo_IdTipo(Integer idTipo);

    // buscar por el id de estado
    List<Reporte> findByEstado_IdEstado(Integer idEstado);

    // NOTA: findByTipo(TipoReporte tipo) y findByEstado(EstadoReporte estado)
    // han sido eliminados ya que findByTipo_IdTipo y findByEstado_IdEstado
    // son más directos cuando se trabaja con IDs en el servicio.
    // El ReporteService ahora obtiene las entidades completas antes de pasarlas al Reporte.
}
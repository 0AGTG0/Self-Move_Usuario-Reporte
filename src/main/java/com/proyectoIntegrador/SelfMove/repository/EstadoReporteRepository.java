package com.proyectoIntegrador.SelfMove.repository;

import com.proyectoIntegrador.SelfMove.entity.EstadoReporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoReporteRepository extends JpaRepository<EstadoReporte, Integer> {
    Optional<EstadoReporte> findByNombreEstado(String nombreEstado);
}
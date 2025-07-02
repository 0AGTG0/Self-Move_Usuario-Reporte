package com.proyectoIntegrador.SelfMove.repository;

import com.proyectoIntegrador.SelfMove.entity.TipoReporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoReporteRepository extends JpaRepository<TipoReporte, Integer> {
    boolean existsByNombreTipo(String nombreTipo);
    Optional<TipoReporte> findByNombreTipo(String nombreTipo);
}
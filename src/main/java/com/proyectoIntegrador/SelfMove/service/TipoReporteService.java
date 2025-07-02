package com.proyectoIntegrador.SelfMove.service;

import com.proyectoIntegrador.SelfMove.entity.TipoReporte;
import com.proyectoIntegrador.SelfMove.repository.TipoReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TipoReporteService {

    @Autowired
    private TipoReporteRepository tipoReporteRepository;

    //buscar por id
    public Optional<TipoReporte> obtenerPorId(Integer id) {
        return tipoReporteRepository.findById(id);
    }

    // Registrar nuevo tipo de reporte
    public TipoReporte registrar(String nombreTipo) {
        // Verificar que no exista un tipo con ese mismo nombre
        if (tipoReporteRepository.existsByNombreTipo(nombreTipo)) {
            throw new IllegalArgumentException("El tipo de reporte ya existe.");
        }
        return tipoReporteRepository.save(new TipoReporte(nombreTipo));
    }

    // Obtener todos los tipos de reporte
    public List<TipoReporte> obtenerTodos() {
        return tipoReporteRepository.findAll();
    }

    // Buscar por nombre de tipo
    public Optional<TipoReporte> buscarPorNombre(String nombreTipo) {
        return tipoReporteRepository.findByNombreTipo(nombreTipo);
    }

    // actualizar el nombre de un tipo por su id
    public TipoReporte actualizarNombre(Integer id, String nuevoNombre) {
        Optional<TipoReporte> tipoOpt = tipoReporteRepository.findById(id);
        if (tipoOpt.isPresent()) {
            TipoReporte tipo = tipoOpt.get();
            tipo.setNombreTipo(nuevoNombre);
            return tipoReporteRepository.save(tipo);
        } else {
            throw new NoSuchElementException("No se encontró el tipo de reporte con ID: " + id);
        }
    }
}
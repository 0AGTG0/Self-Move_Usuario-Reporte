package com.proyectoIntegrador.SelfMove.service;

import com.proyectoIntegrador.SelfMove.entity.EstadoReporte;
import com.proyectoIntegrador.SelfMove.repository.EstadoReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EstadoReporteService {

    @Autowired
    private EstadoReporteRepository estadoReporteRepository;

    // crear un nuevo estado
    public EstadoReporte crear(String nombreEstado) {
        EstadoReporte estado = new EstadoReporte(nombreEstado);
        return estadoReporteRepository.save(estado);
    }

    // obtener todos los estados
    public List<EstadoReporte> obtenerTodos() {
        return estadoReporteRepository.findAll();
    }

    // obtener un estado por su id
    public Optional<EstadoReporte> obtenerPorId(Integer id) {
        return estadoReporteRepository.findById(id);
    }

    //obtener un estado por su nombre exacto
    public Optional<EstadoReporte> obtenerPorNombreExacto(String nombreEstado) {
        return estadoReporteRepository.findByNombreEstado(nombreEstado);
    }

    // actualizar el nombre de un estado por su id
    public EstadoReporte actualizarNombre(Integer id, String nuevoNombre) {
        return estadoReporteRepository.findById(id)
                .map(e -> {
                    e.setNombreEstado(nuevoNombre);
                    return estadoReporteRepository.save(e);
                })
                .orElseThrow(() -> new NoSuchElementException("No se encontró el estado con id: " + id));
    }
}
package com.proyectoIntegrador.SelfMove.controller;

import com.proyectoIntegrador.SelfMove.entity.EstadoReporte;
import com.proyectoIntegrador.SelfMove.service.EstadoReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/SelfMove/estadoReporte")
public class EstadoReporteController {

    @Autowired
    private EstadoReporteService estadoReporteService;

    // POST /SelfMove/estadoReporte crear nuevo estado
    @PostMapping
    public ResponseEntity<EstadoReporte> crear(@RequestParam String nombreEstado) {
        return ResponseEntity.ok(estadoReporteService.crear(nombreEstado));
    }

    // GET /SelfMove/estadoReporte obtener todos los estados
    @GetMapping("/todos")
    public ResponseEntity<List<EstadoReporte>> obtenerTodos() {
        return ResponseEntity.ok(estadoReporteService.obtenerTodos());
    }

    // GET /SelfMove/estadoReporte/{id} buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<EstadoReporte> obtenerPorId(@PathVariable Integer id) {
        Optional<EstadoReporte> estado = estadoReporteService.obtenerPorId(id);
        return estado.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /SelfMove/estadoReporte/buscar/exacto buscar por nombre exacto
    @GetMapping("/buscar/exacto")
    public ResponseEntity<EstadoReporte> buscarPorNombreExacto(@RequestParam String nombre) {
        Optional<EstadoReporte> estado = estadoReporteService.obtenerPorNombreExacto(nombre);
        return estado.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PATCH /SelfMove/estadoReporte/actualizar/{id} actualizar el nombre del estado
    @PatchMapping("/actualizar/{id}")
    public ResponseEntity<EstadoReporte> actualizarNombre(
            @PathVariable Integer id,
            @RequestParam String nuevoNombre) {
        try {
            EstadoReporte actualizado = estadoReporteService.actualizarNombre(id, nuevoNombre);
            return ResponseEntity.ok(actualizado);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
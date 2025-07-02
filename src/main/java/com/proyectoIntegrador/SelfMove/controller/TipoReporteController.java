package com.proyectoIntegrador.SelfMove.controller;

import com.proyectoIntegrador.SelfMove.entity.TipoReporte;
import com.proyectoIntegrador.SelfMove.service.TipoReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/SelfMove/tipoReporte")
public class TipoReporteController {

    @Autowired
    private TipoReporteService tipoReporteService;

    // POST /SelfMove/tipoReporte para registrar un nuevo tipo
    @PostMapping
    public ResponseEntity<TipoReporte> registrar(@RequestParam String nombreTipo) {
        try {
            TipoReporte nuevo = tipoReporteService.registrar(nombreTipo);
            return ResponseEntity.ok(nuevo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // GET /SelfMove/tipoReporte para obtener todos los tipos
    @GetMapping("/todos")
    public ResponseEntity<List<TipoReporte>> obtenerTodos() {
        return ResponseEntity.ok(tipoReporteService.obtenerTodos());
    }

    // GET /SelfMove/tipoReporte/{id} para obtener un tipo reporte por su id
    @GetMapping("/{id}")
    public ResponseEntity<TipoReporte> obtenerPorId(@PathVariable Integer id) {
        Optional<TipoReporte> tipoReporte = tipoReporteService.obtenerPorId(id);
        return tipoReporte.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /SelfMove/tiporeporte/buscar/exacto?nombreTipo=... para obtener un tipo especifico
    @GetMapping("/buscar/exacto")
    public ResponseEntity<TipoReporte> buscarPorNombre(@RequestParam String nombreTipo) {
        Optional<TipoReporte> tipo = tipoReporteService.buscarPorNombre(nombreTipo);
        return tipo.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Patch /SelfMove/tipoReporte/actualizar/{id}?nuevoNombre=... actualizar solo el nommbre de tipo
    @PatchMapping("/actualizar/{id}")
    public ResponseEntity<TipoReporte> actualizarNombreTipo(
            @PathVariable Integer id,
            @RequestParam String nuevoNombre
    ) {
        try {
            TipoReporte actualizado = tipoReporteService.actualizarNombre(id, nuevoNombre);
            return ResponseEntity.ok(actualizado);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
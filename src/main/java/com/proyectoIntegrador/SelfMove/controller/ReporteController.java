package com.proyectoIntegrador.SelfMove.controller;

import com.proyectoIntegrador.SelfMove.dto.ReporteRequestDTO; // Importar DTO de entrada
import com.proyectoIntegrador.SelfMove.dto.ReporteResponseDTO; // Importar DTO de salida
import com.proyectoIntegrador.SelfMove.service.ReporteService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.NoSuchElementException; // Para manejar excepciones específicas

@RestController
@RequestMapping("/SelfMove/reporte")
public class ReporteController {

    private final ReporteService reporteService;

    // Constructor para inyección de dependencias
    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    // POST /SelfMove/reporte para crear un nuevo reporte
    // Ahora recibe un ReporteRequestDTO y devuelve un ReporteResponseDTO
    @PostMapping
    public ResponseEntity<ReporteResponseDTO> crearReporte(@RequestBody ReporteRequestDTO reporteRequestDTO) {
        try {
            ReporteResponseDTO creado = reporteService.crearReporte(reporteRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (RuntimeException e) {
            // Captura excepciones de entidades no encontradas (usuario, tipo, estado)
            return ResponseEntity.badRequest().body(null); // O un mensaje de error más específico
        }
    }

    // GET /SelfMove/reporte obtener todos los reportes de todos los usuarios
    // Ahora devuelve una lista de ReporteResponseDTO
    @GetMapping
    public ResponseEntity<List<ReporteResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(reporteService.obtenerTodos());
    }

    // GET /SelfMove/reporte/{folio} obtener un reporte por su folio
    // Ahora devuelve ReporteResponseDTO
    @GetMapping("/{folio}")
    public ResponseEntity<ReporteResponseDTO> obtenerPorFolio(@PathVariable Integer folio) {
        return reporteService.obtenerPorFolio(folio)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /SelfMove/reporte/usuario/{id} obtener todos los reportes de un usuario por su id
    // Ahora devuelve una lista de ReporteResponseDTO
    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<ReporteResponseDTO>> obtenerPorIdUsuario(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(reporteService.obtenerPorIdUsuario(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Si el usuario no existe
        }
    }

    // GET /SelfMove/reporte/anonimos obtener todos los reportes que tengan valor null en el id del usuario
    // Ahora devuelve una lista de ReporteResponseDTO
    @GetMapping("/anonimos")
    public ResponseEntity<List<ReporteResponseDTO>> obtenerReportesAnonimos() {
        return ResponseEntity.ok(reporteService.obtenerReportesAnonimos());
    }

    // PUT /SelfMove/reporte/actualizar/{folio} actualizar todos los campos de un reporte
    // Ahora recibe un ReporteRequestDTO y devuelve un ReporteResponseDTO
    @PutMapping("/actualizar/{folio}")
    public ResponseEntity<ReporteResponseDTO> actualizarReporte(@PathVariable Integer folio, @RequestBody ReporteRequestDTO nuevoReporteDTO) {
        try {
            return reporteService.actualizarReporte(folio, nuevoReporteDTO)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // O un mensaje de error más específico
        }
    }

    // PATCH /SelfMove/reporte/contenido/{folio} para actualizar el contenido del reporte
    // Ahora devuelve ReporteResponseDTO
    @PatchMapping("/contenido/{folio}")
    public ResponseEntity<ReporteResponseDTO> actualizarContenido(@PathVariable Integer folio, @RequestParam String nuevoContenido) {
        return reporteService.actualizarContenido(folio, nuevoContenido)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /SelfMove/reporte/tipo/{idTipo}
    // Ahora devuelve una lista de ReporteResponseDTO
    @GetMapping("/tipo/{idTipo}")
    public ResponseEntity<List<ReporteResponseDTO>> obtenerPorTipo(@PathVariable Integer idTipo) {
        try {
            return ResponseEntity.ok(reporteService.obtenerPorTipo(idTipo));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Si el tipo no existe
        }
    }

    // GET /SelfMove/reporte/estado/{idEstado}
    // Ahora devuelve una lista de ReporteResponseDTO
    @GetMapping("/estado/{idEstado}")
    public ResponseEntity<List<ReporteResponseDTO>> obtenerPorEstado(@PathVariable Integer idEstado) {
        try {
            return ResponseEntity.ok(reporteService.obtenerPorEstado(idEstado));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Si el estado no existe
        }
    }

    // DELETE /SelfMove/reporte/borrar/{folio} para borrar un reporte por su folio
    @DeleteMapping("/borrar/{folio}")
    public ResponseEntity<String> eliminarReporte(@PathVariable Integer folio) {
        boolean eliminado = reporteService.eliminarReportePorFolio(folio);
        if (eliminado) {
            return ResponseEntity.ok("Reporte con folio " + folio + " eliminado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el reporte con folio " + folio);
        }
    }
}
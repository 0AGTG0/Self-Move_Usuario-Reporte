package com.proyectoIntegrador.SelfMove.service;

import com.proyectoIntegrador.SelfMove.entity.Reporte;
import com.proyectoIntegrador.SelfMove.entity.Usuario;
import com.proyectoIntegrador.SelfMove.entity.TipoReporte;
import com.proyectoIntegrador.SelfMove.entity.EstadoReporte;
import com.proyectoIntegrador.SelfMove.repository.ReporteRepository;
import com.proyectoIntegrador.SelfMove.repository.UsuarioRepository; // Importar
import com.proyectoIntegrador.SelfMove.repository.TipoReporteRepository; // Importar
import com.proyectoIntegrador.SelfMove.repository.EstadoReporteRepository; // Importar
import com.proyectoIntegrador.SelfMove.dto.ReporteRequestDTO; // Importar el DTO de entrada
import com.proyectoIntegrador.SelfMove.dto.ReporteResponseDTO; // Importar el DTO de salida

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors; // Para mapear listas

@Service
public class ReporteService {

    private final ReporteRepository reporteRepository;
    private final UsuarioRepository usuarioRepository; // Inyectar
    private final TipoReporteRepository tipoReporteRepository; // Inyectar
    private final EstadoReporteRepository estadoReporteRepository; // Inyectar

    // Constructor para inyección de dependencias
    @Autowired // @Autowired en constructor es opcional desde Spring 4.3 si solo hay uno
    public ReporteService(ReporteRepository reporteRepository,
                          UsuarioRepository usuarioRepository,
                          TipoReporteRepository tipoReporteRepository,
                          EstadoReporteRepository estadoReporteRepository) {
        this.reporteRepository = reporteRepository;
        this.usuarioRepository = usuarioRepository;
        this.tipoReporteRepository = tipoReporteRepository;
        this.estadoReporteRepository = estadoReporteRepository;
    }

    /**
     * Crea un nuevo reporte a partir de un DTO de solicitud.
     * Busca las entidades relacionadas (Usuario, TipoReporte, EstadoReporte) por sus IDs.
     * @param reporteRequestDTO El DTO que contiene los IDs y el contenido del reporte.
     * @return El ReporteResponseDTO del reporte creado.
     * @throws RuntimeException Si alguna entidad relacionada no se encuentra.
     */
    @Transactional
    public ReporteResponseDTO crearReporte(ReporteRequestDTO reporteRequestDTO) {
        Reporte reporte = new Reporte();

        // 1. Asignar Usuario (puede ser nulo para reportes anónimos)
        if (reporteRequestDTO.getIdUsuario() != null) {
            Usuario usuario = usuarioRepository.findById(reporteRequestDTO.getIdUsuario())
                    .orElseThrow(() -> new RuntimeException("Usuario con ID " + reporteRequestDTO.getIdUsuario() + " no encontrado."));
            reporte.setUsuario(usuario);
        } else {
            reporte.setUsuario(null); // Asegurar que sea NULL para reportes anónimos
        }

        // 2. Asignar TipoReporte
        TipoReporte tipoReporte = tipoReporteRepository.findById(reporteRequestDTO.getIdTipo())
                .orElseThrow(() -> new RuntimeException("Tipo de Reporte con ID " + reporteRequestDTO.getIdTipo() + " no encontrado."));
        reporte.setTipo(tipoReporte);

        // 3. Asignar EstadoReporte
        EstadoReporte estadoReporte = estadoReporteRepository.findById(reporteRequestDTO.getIdEstado())
                .orElseThrow(() -> new RuntimeException("Estado de Reporte con ID " + reporteRequestDTO.getIdEstado() + " no encontrado."));
        reporte.setEstado(estadoReporte);

        // 4. Asignar contenido y fecha/hora
        reporte.setContenido(reporteRequestDTO.getContenido());
        // Si fechaHoraReporte no viene en el DTO, la BD usará DEFAULT CURRENT_TIMESTAMP
        if (reporteRequestDTO.getFechaHoraReporte() != null) {
            reporte.setFechaHoraReporte(reporteRequestDTO.getFechaHoraReporte());
        } else {
            reporte.setFechaHoraReporte(LocalDateTime.now()); // Asegurar que se setee si no viene del DTO
        }

        Reporte reporteGuardado = reporteRepository.save(reporte);
        return convertirA_ReporteResponseDTO(reporteGuardado);
    }

    /**
     * Convierte una entidad Reporte a un ReporteResponseDTO para la salida de la API.
     * @param reporte La entidad Reporte a convertir.
     * @return El DTO de respuesta.
     */
    private ReporteResponseDTO convertirA_ReporteResponseDTO(Reporte reporte) {
        String nombreUsuario = (reporte.getUsuario() != null) ? reporte.getUsuario().getNombre() : null;
        Integer idUsuario = (reporte.getUsuario() != null) ? reporte.getUsuario().getIdUsuario() : null;

        return new ReporteResponseDTO(
                reporte.getFolio(),
                idUsuario,
                nombreUsuario,
                reporte.getTipo().getIdTipo(),
                reporte.getTipo().getNombreTipo(),
                reporte.getEstado().getIdEstado(),
                reporte.getEstado().getNombreEstado(),
                reporte.getContenido(),
                reporte.getFechaHoraReporte()
        );
    }

    // GET - Buscar por folio (clave primaria)
    public Optional<ReporteResponseDTO> obtenerPorFolio(Integer folio) {
        return reporteRepository.findById(folio)
                .map(this::convertirA_ReporteResponseDTO); // Mapea la entidad a DTO si se encuentra
    }

    // GET - Obtener todos los reportes
    public List<ReporteResponseDTO> obtenerTodos() {
        return reporteRepository.findAll().stream()
                .map(this::convertirA_ReporteResponseDTO) // Mapea cada entidad a DTO
                .collect(Collectors.toList());
    }

    // GET - Buscar por ID de usuario (reportes de un usuario específico)
    public List<ReporteResponseDTO> obtenerPorIdUsuario(Integer idUsuario) {
        // Primero, verificar que el usuario exista
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario con ID " + idUsuario + " no encontrado."));

        return reporteRepository.findByUsuario(usuario).stream() // Usamos el método findByUsuario(Usuario)
                .map(this::convertirA_ReporteResponseDTO)
                .collect(Collectors.toList());
    }

    // GET - Reportes anónimos (usuario NULL)
    public List<ReporteResponseDTO> obtenerReportesAnonimos() {
        return reporteRepository.findByUsuarioIsNull().stream()
                .map(this::convertirA_ReporteResponseDTO)
                .collect(Collectors.toList());
    }

    // PUT - Actualizar completamente un reporte existente
    // Este método ahora recibe un DTO de solicitud y el folio
    @Transactional
    public Optional<ReporteResponseDTO> actualizarReporte(Integer folio, ReporteRequestDTO nuevoReporteDTO) {
        return reporteRepository.findById(folio).map(reporteExistente -> {
            // Actualizar usuario (si viene en el DTO y es diferente)
            if (nuevoReporteDTO.getIdUsuario() != null) {
                Usuario nuevoUsuario = usuarioRepository.findById(nuevoReporteDTO.getIdUsuario())
                        .orElseThrow(() -> new RuntimeException("Usuario con ID " + nuevoReporteDTO.getIdUsuario() + " no encontrado para actualizar."));
                reporteExistente.setUsuario(nuevoUsuario);
            } else {
                reporteExistente.setUsuario(null); // Para hacer el reporte anónimo si se pasa null
            }

            // Actualizar tipo
            TipoReporte nuevoTipo = tipoReporteRepository.findById(nuevoReporteDTO.getIdTipo())
                    .orElseThrow(() -> new RuntimeException("Tipo de Reporte con ID " + nuevoReporteDTO.getIdTipo() + " no encontrado para actualizar."));
            reporteExistente.setTipo(nuevoTipo);

            // Actualizar estado
            EstadoReporte nuevoEstado = estadoReporteRepository.findById(nuevoReporteDTO.getIdEstado())
                    .orElseThrow(() -> new RuntimeException("Estado de Reporte con ID " + nuevoReporteDTO.getIdEstado() + " no encontrado para actualizar."));
            reporteExistente.setEstado(nuevoEstado);

            // Actualizar contenido y fecha
            reporteExistente.setContenido(nuevoReporteDTO.getContenido());
            if (nuevoReporteDTO.getFechaHoraReporte() != null) {
                reporteExistente.setFechaHoraReporte(nuevoReporteDTO.getFechaHoraReporte());
            }

            Reporte reporteActualizado = reporteRepository.save(reporteExistente);
            return convertirA_ReporteResponseDTO(reporteActualizado);
        });
    }

    // PATCH - Cambiar solo el contenido del reporte
    public Optional<ReporteResponseDTO> actualizarContenido(Integer folio, String nuevoContenido) {
        return reporteRepository.findById(folio).map(reporte -> {
            reporte.setContenido(nuevoContenido);
            Reporte reporteActualizado = reporteRepository.save(reporte);
            return convertirA_ReporteResponseDTO(reporteActualizado);
        });
    }

    // buscar por el tipo de reporte por su id
    public List<ReporteResponseDTO> obtenerPorTipo(Integer idTipo) {
        // Primero, verificar que el tipo exista
        TipoReporte tipoReporte = tipoReporteRepository.findById(idTipo)
                .orElseThrow(() -> new RuntimeException("Tipo de Reporte con ID " + idTipo + " no encontrado."));

        return reporteRepository.findByTipo_IdTipo(tipoReporte.getIdTipo()).stream()
                .map(this::convertirA_ReporteResponseDTO)
                .collect(Collectors.toList());
    }

    // buscar el estado de un reporte por su id
    public List<ReporteResponseDTO> obtenerPorEstado(Integer idEstado) {
        // Primero, verificar que el estado exista
        EstadoReporte estadoReporte = estadoReporteRepository.findById(idEstado)
                .orElseThrow(() -> new RuntimeException("Estado de Reporte con ID " + idEstado + " no encontrado."));

        return reporteRepository.findByEstado_IdEstado(estadoReporte.getIdEstado()).stream()
                .map(this::convertirA_ReporteResponseDTO)
                .collect(Collectors.toList());
    }

    // DELETE (opcional) - Eliminar reporte por su folio (por si algún admin necesita quitarlo)
    @Transactional
    public boolean eliminarReportePorFolio(Integer folio) {
        if (reporteRepository.existsById(folio)) {
            reporteRepository.deleteById(folio);
            return true;
        } else {
            return false;
        }
    }
}
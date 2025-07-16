package com.proyectoIntegrador.SelfMove.service;

import com.proyectoIntegrador.SelfMove.entity.Usuario;
import com.proyectoIntegrador.SelfMove.repository.UsuarioRepository;
import com.proyectoIntegrador.SelfMove.dto.UsuarioResponseDTO; // Importar el nuevo DTO
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors; // Para mapear listas

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Convierte una entidad Usuario a un UsuarioResponseDTO para la salida de la API.
     * Excluye campos sensibles como la contraseña.
     * @param usuario La entidad Usuario a convertir.
     * @return El DTO de respuesta.
     */
    private UsuarioResponseDTO convertirA_UsuarioResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getIdUsuario(),
                usuario.getNombre(),
                usuario.getDireccion(),
                usuario.getCorreo(),
                usuario.getActivo(),
                usuario.getFechaHoraRegistro()
        );
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * Incluye validación de correo existente y hashing de contraseña.
     * @param usuario El objeto Usuario a registrar.
     * @return El usuario registrado con su ID generado.
     * @throws RuntimeException Si el correo ya está registrado.
     */
    @Transactional
    public Usuario registrarUsuario(Usuario usuario) {
        if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) { // Usar isPresent() con Optional
            throw new RuntimeException("El correo electrónico ya está registrado. Por favor, use otro.");
        }

        usuario.setContraseniaHash(passwordEncoder.encode(usuario.getContraseniaHash()));

        if (usuario.getFechaHoraRegistro() == null) {
            usuario.setFechaHoraRegistro(LocalDateTime.now());
        }
        if (usuario.getActivo() == null) {
            usuario.setActivo(true);
        }

        return usuarioRepository.save(usuario);
    }

    /**
     * Obtiene todos los usuarios, mapeados a DTOs de respuesta.
     * @return Una lista de todos los UsuarioResponseDTO.
     */
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> obtenerTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::convertirA_UsuarioResponseDTO) // Mapea cada entidad a DTO
                .collect(Collectors.toList());
    }

    /**
     * Busca un usuario por su ID, mapeado a un DTO de respuesta.
     * @param id El ID del usuario.
     * @return Un Optional que contiene el UsuarioResponseDTO si se encuentra, o Optional.empty() si no.
     */
    @Transactional(readOnly = true)
    public Optional<UsuarioResponseDTO> obtenerPorId(Integer id) {
        return usuarioRepository.findById(id)
                .map(this::convertirA_UsuarioResponseDTO); // Mapea la entidad a DTO si se encuentra
    }

    /**
     * Busca un usuario por su correo electrónico, mapeado a un DTO de respuesta.
     * @param correo El correo electrónico a buscar.
     * @return Un Optional que contiene el UsuarioResponseDTO si se encuentra, o Optional.empty() si no.
     */
    @Transactional(readOnly = true)
    public Optional<UsuarioResponseDTO> buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo)
                .map(this::convertirA_UsuarioResponseDTO);
    }

    /**
     * Obtiene una lista de usuarios activos, mapeados a DTOs de respuesta.
     * @return Lista de UsuarioResponseDTO activos.
     */
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> obtenerActivos() {
        return usuarioRepository.findByActivoTrue().stream()
                .map(this::convertirA_UsuarioResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene una lista de usuarios inactivos, mapeados a DTOs de respuesta.
     * @return Lista de UsuarioResponseDTO inactivos.
     */
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> obtenerInactivos() {
        return usuarioRepository.findByActivoFalse().stream()
                .map(this::convertirA_UsuarioResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca usuarios por una parte de su nombre, mapeados a DTOs de respuesta.
     * @param nombre La cadena a buscar en el nombre.
     * @return Lista de UsuarioResponseDTO que contienen la cadena en su nombre.
     */
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> buscarPorNombre(String nombre) {
        return usuarioRepository.findByNombreContaining(nombre).stream()
                .map(this::convertirA_UsuarioResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca usuarios por una parte de su dirección, mapeados a DTOs de respuesta.
     * @param direccion La cadena a buscar en la dirección.
     * @return Lista de UsuarioResponseDTO que contienen la cadena en su dirección.
     */
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> buscarPorDireccion(String direccion) {
        return usuarioRepository.findByDireccionContaining(direccion).stream()
                .map(this::convertirA_UsuarioResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca usuarios registrados en un rango de fechas, mapeados a DTOs de respuesta.
     * @param desde Fecha y hora de inicio del rango.
     * @param hasta Fecha y hora de fin del rango.
     * @return Lista de UsuarioResponseDTO registrados en el rango especificado.
     */
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> buscarPorFechaRegistro(LocalDateTime desde, LocalDateTime hasta) {
        return usuarioRepository.findByFechaHoraRegistroBetween(desde, hasta).stream()
                .map(this::convertirA_UsuarioResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Desactiva un usuario por su ID.
     * @param id El ID del usuario a desactivar.
     * @throws RuntimeException Si el usuario no es encontrado.
     */
    @Transactional
    public void desactivarUsuario(Integer id) {
        usuarioRepository.findById(id).map(usuario -> {
            usuario.setActivo(false);
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
    }

    /**
     * Elimina un usuario completamente por su ID.
     * @param id El ID del usuario a eliminar.
     * @throws RuntimeException Si el usuario no es encontrado.
     */
    @Transactional
    public void eliminarUsuario(Integer id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        } else {
            throw new RuntimeException("Usuario no encontrado para eliminar.");
        }
    }

    /**
     * Actualiza toda la información de un usuario existente.
     * @param id El ID del usuario a actualizar.
     * @param usuarioActualizado El objeto Usuario con la nueva información.
     * @return El usuario actualizado, o Optional.empty() si no se encontró.
     */
    @Transactional
    public Optional<UsuarioResponseDTO> actualizarUsuario(Integer id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id).map(usuarioExistente -> {
            usuarioExistente.setNombre(usuarioActualizado.getNombre());
            usuarioExistente.setDireccion(usuarioActualizado.getDireccion());
            usuarioExistente.setCorreo(usuarioActualizado.getCorreo());

            if (usuarioActualizado.getContraseniaHash() != null && !usuarioActualizado.getContraseniaHash().isEmpty()) {
                usuarioExistente.setContraseniaHash(passwordEncoder.encode(usuarioActualizado.getContraseniaHash()));
            }
            usuarioExistente.setActivo(usuarioActualizado.getActivo());
            Usuario updatedUser = usuarioRepository.save(usuarioExistente);
            return convertirA_UsuarioResponseDTO(updatedUser); // Devuelve el DTO de respuesta
        });
    }

    /**
     * Actualiza solo la dirección de un usuario por su ID.
     * @param id El ID del usuario.
     * @param nuevaDireccion La nueva dirección.
     * @return El usuario actualizado, o Optional.empty() si no se encontró.
     */
    @Transactional
    public Optional<UsuarioResponseDTO> actualizarDireccion(Integer id, String nuevaDireccion) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setDireccion(nuevaDireccion);
            Usuario updatedUser = usuarioRepository.save(usuario);
            return convertirA_UsuarioResponseDTO(updatedUser); // Devuelve el DTO de respuesta
        });
    }

    /**
     * Autentica un usuario verificando sus credenciales.
     * @param correo El correo electrónico del usuario.
     * @param contraseniaTextoPlano La contraseña ingresada por el usuario en texto plano.
     * @return El objeto Usuario si la autenticación es exitosa, Optional.empty() si falla.
     */
    @Transactional(readOnly = true)
    public Optional<Usuario> autenticarUsuario(String correo, String contraseniaTextoPlano) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (usuario.getActivo() && passwordEncoder.matches(contraseniaTextoPlano, usuario.getContraseniaHash())) {
                return Optional.of(usuario);
            }
        }
        return Optional.empty();
    }
}

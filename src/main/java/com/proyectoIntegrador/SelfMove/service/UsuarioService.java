package com.proyectoIntegrador.SelfMove.service;

import com.proyectoIntegrador.SelfMove.entity.Usuario;
import com.proyectoIntegrador.SelfMove.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Registra un nuevo usuario (verifica que no exista el correo)
    public Usuario registrarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado.");
        }
            usuario.setActivo(true); // Activamos por defecto
            usuario.setFechaHoraRegistro(LocalDateTime.now()); // Ponemos fecha de registro
            return usuarioRepository.save(usuario);
        }

    // Obtener todos los usuarios
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    // Buscar usuario por ID
    public Optional<Usuario> obtenerPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    // Buscar usuario por correo
    public Optional<Usuario> buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    // Buscar usuarios activos
    public List<Usuario> obtenerActivos() {
        return usuarioRepository.findByActivoTrue();
    }

    // Buscar usuarios inactivos
    public List<Usuario> obtenerInactivos() {
        return usuarioRepository.findByActivoFalse();
    }

    // Buscar usuario por una parte de su nombre (regresa todos los que tengan la misma palabra en el)
    public List<Usuario> buscarPorNombre(String nombre) {
        return usuarioRepository.findByNombreContaining(nombre);
    }

    // Buscar usuario por dirección (sucede lo mismo con que con usuario)
    public List<Usuario> buscarPorDireccion(String direccion) {
        return usuarioRepository.findByDireccionContaining(direccion);
    }

    // Buscar por rango de fecha de registro
    public List<Usuario> buscarPorFechaRegistro(LocalDateTime desde, LocalDateTime hasta) {
        return usuarioRepository.findByFechaHoraRegistroBetween(desde, hasta);
    }

    // Desactivar usuario (sin eliminarlo)
    public void desactivarUsuario(Integer id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            Usuario u = usuario.get();
            u.setActivo(false);
            usuarioRepository.save(u);
        } else {
            throw new RuntimeException("Usuario no encontrado.");
        }
    }

    // Eliminar usuario completamente
    public void eliminarUsuario(Integer id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        } else {
            throw new RuntimeException("Usuario no encontrado para eliminar.");
        }
    }

    // actualizar toda la informacion de un usuario
    public Optional<Usuario> actualizarUsuario(Integer id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id).map(usuarioExistente -> {
            usuarioExistente.setNombre(usuarioActualizado.getNombre());
            usuarioExistente.setDireccion(usuarioActualizado.getDireccion());
            usuarioExistente.setCorreo(usuarioActualizado.getCorreo());
            usuarioExistente.setContraseniaHash(usuarioActualizado.getContraseniaHash());
            usuarioExistente.setActivo(usuarioActualizado.getActivo());
            return usuarioRepository.save(usuarioExistente);
        });
    }

    // actualizar la direccion de un usuario por su id
    public Optional<Usuario> actualizarDireccion(Integer id, String nuevaDireccion) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setDireccion(nuevaDireccion);
            return usuarioRepository.save(usuario);
        });
    }
}
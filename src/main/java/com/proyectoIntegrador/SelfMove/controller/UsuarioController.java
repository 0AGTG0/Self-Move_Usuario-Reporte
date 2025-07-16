package com.proyectoIntegrador.SelfMove.controller;

import com.proyectoIntegrador.SelfMove.entity.Usuario;
import com.proyectoIntegrador.SelfMove.service.UsuarioService;
import com.proyectoIntegrador.SelfMove.dto.UsuarioResponseDTO; // Importar el nuevo DTO
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/SelfMove")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // POST /SelfMove/usuario para crear nuevos usuarios
    // Recibe la entidad Usuario directamente para que el servicio pueda hashear la contraseña
    @PostMapping("/usuario")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario nuevo = usuarioService.registrarUsuario(usuario);
            // Opcional: Podrías devolver un UsuarioResponseDTO aquí también si no quieres el hash en la respuesta POST
            return ResponseEntity.ok(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // GET /SelfMove/usuarios para obtener todos los usuarios
    // Ahora devuelve una lista de UsuarioResponseDTO
    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }

    // GET /SelfMove/usuario/{id} para obtener un usuario por su id
    // Ahora devuelve UsuarioResponseDTO
    @GetMapping("/usuario/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerPorId(@PathVariable Integer id) {
        return usuarioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /SelfMove/usuarios/activos para obtener todos los usuarios activos
    // Ahora devuelve una lista de UsuarioResponseDTO
    @GetMapping("/usuarios/activos")
    public ResponseEntity<List<UsuarioResponseDTO>> obtenerActivos() {
        return ResponseEntity.ok(usuarioService.obtenerActivos());
    }

    // GET /SelfMove/usuarios/inactivos para obtener todos los usuarios inactivos
    // Ahora devuelve una lista de UsuarioResponseDTO
    @GetMapping("/usuarios/inactivos")
    public ResponseEntity<List<UsuarioResponseDTO>> obtenerInactivos() {
        return ResponseEntity.ok(usuarioService.obtenerInactivos());
    }

    // GET /SelfMove/usuario/correo?correo=... para buscar usuarios por sus correos
    // Ahora devuelve UsuarioResponseDTO
    @GetMapping("/usuario/correo")
    public ResponseEntity<UsuarioResponseDTO> buscarPorCorreo(@RequestParam String correo) {
        return usuarioService.buscarPorCorreo(correo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /SelfMove/usuarios/nombre?nombre=... para obtener todos los usuarios que tengan una parte
    // de su nombre (ej. buscar Ramirez y devuelve todos los que en el nombre tengan Ramirez)
    // Ahora devuelve una lista de UsuarioResponseDTO
    @GetMapping("/usuarios/nombre")
    public ResponseEntity<List<UsuarioResponseDTO>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(usuarioService.buscarPorNombre(nombre));
    }

    // GET /SelfMove/usuarios/direccion?direccion=... para obtener una lista de usuarios que vivan en la
    // misma zona (caso similar que con usuario)
    // Ahora devuelve una lista de UsuarioResponseDTO
    @GetMapping("/usuarios/direccion")
    public ResponseEntity<List<UsuarioResponseDTO>> buscarPorDireccion(@RequestParam String direccion) {
        return ResponseEntity.ok(usuarioService.buscarPorDireccion(direccion));
    }

    // GET /SelfMove/usuarios/registro?desde=...&hasta=... para buscar usuarios en un determinado
    // rango de tiempo en el que se registraron
    // Ahora devuelve una lista de UsuarioResponseDTO
    @GetMapping("/usuarios/registro")
    public ResponseEntity<List<UsuarioResponseDTO>> buscarPorFechaRegistro(
            @RequestParam LocalDateTime desde,
            @RequestParam LocalDateTime hasta
    ) {
        return ResponseEntity.ok(usuarioService.buscarPorFechaRegistro(desde, hasta));
    }

    // PUT /SelfMove/usuarios/desactivar/{id} para desactivar un usuario por su id sin borrarlo
    @PutMapping("/usuario/desactivar/{id}")
    public ResponseEntity<Void> desactivarUsuario(@PathVariable Integer id) {
        try {
            usuarioService.desactivarUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /SelfMove/usuario/borrar/{id} borrar permanentemente un usuario por su id
    @DeleteMapping("/usuario/borrar/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        try {
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // PUT /SelfMove/usuario/actualizar/{id} para actualizar toda la informacion de un usuario
    // Ahora devuelve UsuarioResponseDTO
    @PutMapping("/usuario/actualizar/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(@PathVariable Integer id, @RequestBody Usuario usuarioActualizado) {
        Optional<UsuarioResponseDTO> actualizado = usuarioService.actualizarUsuario(id, usuarioActualizado);
        return actualizado.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PATCH /SelfMove/usuario/direccion/{id}?nuevaDireccion=...
    // Ahora devuelve UsuarioResponseDTO
    @PatchMapping("/usuario/direccion/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarDireccion(
            @PathVariable Integer id,
            @RequestParam String nuevaDireccion) {
        Optional<UsuarioResponseDTO> actualizado = usuarioService.actualizarDireccion(id, nuevaDireccion);
        return actualizado.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

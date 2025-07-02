package com.proyectoIntegrador.SelfMove.controller;

import com.proyectoIntegrador.SelfMove.entity.Usuario;
import com.proyectoIntegrador.SelfMove.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/SelfMove")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // POST /SelfMove para crear nuevos usuarios
    @PostMapping
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuario) {
        Usuario nuevo = usuarioService.registrarUsuario(usuario);
        return ResponseEntity.ok(nuevo);
    }

    // GET /SelfMove/usuarios para obtener todos los usuarios
    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> obtenerTodos() {
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }

    // GET /SelfMove/usuario/{id} para obtener un usuario por su id
    @GetMapping("/usuario/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Integer id) {
        Optional<Usuario> usuario = usuarioService.obtenerPorId(id);
        return usuario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /SelfMove/usuarios/activos para obtener todos los usuarios activos
    @GetMapping("/usuarios/activos")
    public ResponseEntity<List<Usuario>> obtenerActivos() {
        return ResponseEntity.ok(usuarioService.obtenerActivos());
    }

    // GET /SelfMove/usuarios/inactivos para obtener todos los usuarios inactivos
    @GetMapping("/usuarios/inactivos")
    public ResponseEntity<List<Usuario>> obtenerInactivos() {
        return ResponseEntity.ok(usuarioService.obtenerInactivos());
    }

    // GET /SelfMove/usuario/correo?correo=... para buscar usuarios por sus correos
    @GetMapping("/usuario/correo")
    public ResponseEntity<Usuario> buscarPorCorreo(@RequestParam String correo) {
        Optional<Usuario> usuario = usuarioService.buscarPorCorreo(correo);
        return usuario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /SelfMove/usuarios/nombre?nombre=... para obtener todos los usuarios que tengan una parte
    // de su nombre (ej. buscar Ramirez y devuelve todos los que en el nombre tengan Ramirez)
    @GetMapping("/usuarios/nombre")
    public ResponseEntity<List<Usuario>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(usuarioService.buscarPorNombre(nombre));
    }

    // GET /SelfMove/usuarios/direccion?direccion=... para obtener una lista de usuarios que vivan en la
    // misma zona (caso similar que con nombre)
    @GetMapping("/usuarios/direccion")
    public ResponseEntity<List<Usuario>> buscarPorDireccion(@RequestParam String direccion) {
        return ResponseEntity.ok(usuarioService.buscarPorDireccion(direccion));
    }

    // GET /SelfMove/usuarios/registro?desde=...&hasta=... para buscar usuarios en un determinado
    // rango de tiempo en el que se registraron
    @GetMapping("/usuarios/registro")
    public ResponseEntity<List<Usuario>> buscarPorFechaRegistro(
            @RequestParam LocalDateTime desde,
            @RequestParam LocalDateTime hasta
    ) {
        return ResponseEntity.ok(usuarioService.buscarPorFechaRegistro(desde, hasta));
    }

    // PUT /SelfMove/usuarios/desactivar/{id} para desactivar un usuario por su id sin borrarlo
    @PutMapping("/usuario/desactivar/{id}")
    public ResponseEntity<Void> desactivarUsuario(@PathVariable Integer id) {
        usuarioService.desactivarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // DELETE /SelfMove/usuario/borrar/{id} borrar permanentemente un usuario por su id
    @DeleteMapping("/usuario/borrar/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // PUT /SelfMove/usuario/actualizar/{id} para actualizar toda la informacion de un usuario
    @PutMapping("/usuario/actualizar/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Integer id, @RequestBody Usuario usuarioActualizado) {
        Optional<Usuario> actualizado = usuarioService.actualizarUsuario(id, usuarioActualizado);
        return actualizado.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //ejemplo de un PATCH que solo actualiza un dato de todos los que tiene usuario
    // PATCH /SelfMove/usuario/direccion/{id}?nuevaDireccion=...
    @PatchMapping("/usuario/direccion/{id}")
    public ResponseEntity<Usuario> actualizarDireccion(
            @PathVariable Integer id,
            @RequestParam String nuevaDireccion) {
        Optional<Usuario> actualizado = usuarioService.actualizarDireccion(id, nuevaDireccion);
        return actualizado.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
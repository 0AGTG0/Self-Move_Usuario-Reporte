package com.proyectoIntegrador.SelfMove.repository;

import com.proyectoIntegrador.SelfMove.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

 /*
    Esto le dice a Spring: “Quiero que me des todas las funciones CRUD
    (guardar, buscar, eliminar, actualizar) para la entidad Usuario,
     cuya clave primaria es de tipo Integer.”
 */

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    /*
      Buscar usuario por correo (Spring genera el SQL automáticamente)
      Devuelve Optional porque puede tener un resultado o estar vacio
    */
    Optional<Usuario> findByCorreo(String correo);

    // Verificar si existe un correo ya registrado
    boolean existsByCorreo(String correo);

    // buscar usuarios activos
    List<Usuario> findByActivoTrue();

    // buscar usuarios inactivos
    List<Usuario> findByActivoFalse();

    //Estos funcionan al buscar una parte del texto dentro de lo guardado en la variable
    // buscar por nombre
    List<Usuario> findByNombreContaining(String nombre);
    // buscar por nombre
    List<Usuario> findByDireccionContaining(String direccion);

    // buscar entre fechas de registro
    List<Usuario> findByFechaHoraRegistroBetween(LocalDateTime desde, LocalDateTime hasta);
}
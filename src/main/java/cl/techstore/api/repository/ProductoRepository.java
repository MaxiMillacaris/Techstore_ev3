package cl.techstore.api.repository;

import cl.techstore.api.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de acceso a datos para la entidad Producto.
 * Extiende JpaRepository para operaciones CRUD automáticas.
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Devuelve solo productos activos (no eliminados lógicamente)
    List<Producto> findByActivoTrue();

    // Busca un producto por ID solo si está activo
    Optional<Producto> findByIdAndActivoTrue(Long id);
}

package cl.techstore.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Entidad JPA que representa la tabla "productos" en la base de datos.
 * Solo existen productos en la BD — no hay tabla de usuarios.
 */
@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 255)
    private String descripcion;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false, length = 50)
    private String categoria;

    /**
     * Campo de eliminación lógica.
     * true  = producto activo (visible en catálogo)
     * false = producto eliminado (oculto del catálogo, pero persiste en BD)
     */
    @Column(nullable = false)
    private Boolean activo = true;
}

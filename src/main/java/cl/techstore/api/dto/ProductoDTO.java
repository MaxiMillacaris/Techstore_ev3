package cl.techstore.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO para crear y modificar productos.
 * Objeto de transferencia de datos entre el cliente y el servicio.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private String categoria;
    private Boolean activo;
}

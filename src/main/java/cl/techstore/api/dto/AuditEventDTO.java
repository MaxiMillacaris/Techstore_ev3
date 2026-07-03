package cl.techstore.api.dto;

public record AuditEventDTO(
        String accion,
        Long productoId,
        String nombre,
        String usuario,
        String fecha
) {
}
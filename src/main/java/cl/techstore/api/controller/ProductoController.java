package cl.techstore.api.controller;

import cl.techstore.api.dto.ProductoDTO;
import cl.techstore.api.model.Producto;
import cl.techstore.api.service.AuditService;
import cl.techstore.api.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 
Controlador REST de productos.
Todos los endpoints requieren token JWT válido en el header:
Authorization: Bearer <token>*
GET    /api/productos        → 200 OK       Listar todos los activos
POST   /api/productos        → 201 Created  Crear un producto nuevo
PUT    /api/productos/{id}   → 200 OK       Modificar un producto existente
DELETE /api/productos/{id}   → 204 No Content  Eliminación lógica (activo=false)*/
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private AuditService auditService;

    // ── Listar todos los productos activos ──────────────────────────────────
    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(productoService.listarTodos());
    }

    // ── Obtener un producto por ID ──────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtener(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productoService.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ── Crear un producto nuevo ─────────────────────────────────────────────
    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody ProductoDTO dto,
                                          Authentication authentication) {
        Producto creado = productoService.crear(dto);

        auditService.enviarAuditoria(
                "CREAR",
                creado,
                obtenerUsuario(authentication)
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // ── Modificar un producto existente ────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<Producto> modificar(@PathVariable Long id,
                                               @RequestBody ProductoDTO dto,
                                               Authentication authentication) {
        try {
            Producto modificado = productoService.modificar(id, dto);

            auditService.enviarAuditoria(
                    "MODIFICAR",
                    modificado,
                    obtenerUsuario(authentication)
            );

            return ResponseEntity.ok(modificado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ── Eliminación lógica: activo = false ─────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id,
                                         Authentication authentication) {
        try {
            Producto eliminado = productoService.eliminar(id);

            auditService.enviarAuditoria(
                    "ELIMINAR",
                    eliminado,
                    obtenerUsuario(authentication)
            );

            return ResponseEntity.noContent().build();   // 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private String obtenerUsuario(Authentication authentication) {
        return authentication != null ? authentication.getName() : "usuario_desconocido";
    }
}

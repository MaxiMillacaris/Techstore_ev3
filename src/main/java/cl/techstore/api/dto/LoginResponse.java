package cl.techstore.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO de respuesta del login.
 * Contiene el token JWT generado, su tipo y tiempo de expiración en segundos.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String tipo;
    private String expiracion;

    public LoginResponse(String token) {
        this.token = token;
        this.tipo = "Bearer";
        this.expiracion = "3600";
    }
}

package cr.ac.una.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OferenteRegistroDTO {

    @NotBlank(message = "El nombre es obligatorio.")
    private String nombre;

    @NotBlank(message = "Los apellidos son obligatorios.")
    private String apellidos;

    @NotBlank(message = "La nacionalidad es obligatoria.")
    private String nacionalidad;

    @NotBlank(message = "El correo es obligatorio.")
    @Email(message = "Debe ingresar un correo válido (ejemplo@dominio.com).")
    private String correopersonal;

    @NotBlank(message = "La residencia es obligatoria.")
    private String residencia;

    @NotBlank(message = "La contraseña es obligatoria.")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres.")
    private String password;

    @NotBlank(message = "El teléfono es obligatorio.")
    @Pattern(regexp = "\\d{8}", message = "El teléfono debe tener exactamente 8 dígitos numéricos")
    private String telefono;
}